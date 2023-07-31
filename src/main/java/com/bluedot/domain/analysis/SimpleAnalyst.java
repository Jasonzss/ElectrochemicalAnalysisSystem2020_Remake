package com.bluedot.domain.analysis;

import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.application.AlgorithmService;
import com.bluedot.domain.algorithm.exception.AlgorithmException;
import com.bluedot.domain.analysis.model.AnalyzedData;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.utils.BigDecimalUtil;
import com.bluedot.infrastructure.utils.LinearEquation;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/16 - 15:56
 */
@Component
public class SimpleAnalyst implements Analyst{
    @Inject
    private AlgorithmService manager;

    @Override
    public LinearEquation getModel(List<CurveData> dataList, String modelAlgorithmId) {
        try{
            Algorithm algorithm = manager.getAlgorithm(modelAlgorithmId);
            return (LinearEquation) algorithm.execute(dataList);
        } catch (ClassCastException e){
            throw new AlgorithmException(CommonErrorCode.E_3021);
        }
    }

    @Override
    public AnalysisResult analysis(AnalyzedData train, AnalyzedData test) {
        AnalysisResult result = new AnalysisResult();
        BigDecimal[] trainReality = train.getConcentrationSet().getValues().toArray(new BigDecimal[0]);
        BigDecimal[] trainPrediction = train.getPredictedConcentrationSet().getValues().toArray(new BigDecimal[0]);

        result.setRc2(getR2(trainReality, trainPrediction).doubleValue());
        result.setRmsec(getRMSE(trainReality, trainPrediction).doubleValue());
        result.setMaec(getMAE(trainReality, trainPrediction).doubleValue());

        BigDecimal[] testReality = train.getConcentrationSet().getValues().toArray(new BigDecimal[0]);
        BigDecimal[] testPrediction = train.getPredictedConcentrationSet().getValues().toArray(new BigDecimal[0]);

        result.setRp2(getR2(testReality, testPrediction).doubleValue());
        result.setRmsep(getRMSE(testReality, testPrediction).doubleValue());
        result.setMaep(getMAE(testReality, testPrediction).doubleValue());
        result.setRpd(getPRD(testReality, testPrediction).doubleValue());

        LinearEquation trainEq = calculateLinearEquation(trainReality, trainPrediction);
        LinearEquation testEq = calculateLinearEquation(testReality, testPrediction);
        result.setTrainEquation(trainEq);
        result.setTestEquation(testEq);

        return result;
    }

    /**
     * 根据真实值和预测值求决定系数
     *
     * @param reality    真实值数组
     * @param prediction 预测值
     * @return 决定系数R2
     */
    private BigDecimal getR2(BigDecimal[] reality, BigDecimal[] prediction) {
        if (reality.length != prediction.length) {
            throw new IndexOutOfBoundsException();
        }
        BigDecimal mean = getMean(reality);
        BigDecimal res = BigDecimal.ONE;
        for (int i = 0; i < reality.length; i++) {
            res = res.subtract(prediction[i].subtract(reality[i]).pow(2).divide(mean.subtract(reality[i]).pow(2), RoundingMode.HALF_UP));
        }
        return res;
    }

    /**
     * 根据真实值和预测值求均方根误差
     *
     * @param reality    真实值
     * @param prediction 预测值
     * @return 均方根误差RMSE
     */
    private BigDecimal getRMSE(BigDecimal[] reality, BigDecimal[] prediction) {
        if (reality.length != prediction.length) {
            throw new IndexOutOfBoundsException();
        }
        BigDecimal res = BigDecimal.ZERO;
        for (int i = 0; i < reality.length; i++) {
            res = res.add(reality[i].subtract(prediction[i]).pow(2));
        }

        return res.divide(new BigDecimal(reality.length), RoundingMode.HALF_UP);
    }

    /**
     * 根据真实值和预测值求平均绝对误差
     *
     * @param reality    真实值
     * @param prediction 预测值
     * @return 平均绝对误差MAE
     */
    private BigDecimal getMAE(BigDecimal[] reality, BigDecimal[] prediction) {
        if (reality.length != prediction.length) {
            throw new IndexOutOfBoundsException();
        }
        BigDecimal res = BigDecimal.ZERO;
        for (int i = 0; i < reality.length; i++) {
            res = res.add(prediction[i].subtract(reality[i]).abs());
        }
        return res.divide(new BigDecimal(reality.length), RoundingMode.HALF_UP);
    }

    /**
     * 根据真实值和预测值求预测偏差
     *
     * @param reality    真实值
     * @param prediction 预测值
     * @return 预测偏差PRD
     */
    private BigDecimal getPRD(BigDecimal[] reality, BigDecimal[] prediction) {
        if (reality.length != prediction.length) {
            throw new IndexOutOfBoundsException();
        }
        //求标准差Stdev
        BigDecimal mean = getMean(reality);
        BigDecimal stdev = BigDecimal.ZERO;
        for (BigDecimal aDouble : reality) {
            stdev = stdev.add(aDouble.subtract(mean).pow(2));
        }

        stdev = BigDecimalUtil.sqrt(stdev.divide(new BigDecimal(reality.length), RoundingMode.HALF_UP));
        BigDecimal rmse = getRMSE(reality, prediction);
        //返回标准差与均方根误差的结果即为预测偏差
        return stdev.divide(rmse,RoundingMode.HALF_UP);
    }


    /**
     * 求Double数组均值
     *
     * @param data 数据数组
     * @return Double数组的均值
     */
    private BigDecimal getMean(BigDecimal[] data) {
        BigDecimal mean = BigDecimal.ZERO;
        for (BigDecimal datum : data) {
            mean = mean.add(datum);
        }
        return mean.divide(new BigDecimal(data.length), RoundingMode.HALF_UP);
    }

    /**
     * 根据真实数据和预测数据求拟合方程
     *
     * @param reality    真实值
     * @param prediction 预测值
     * @return 方程
     */
    private LinearEquation calculateLinearEquation(BigDecimal[] reality, BigDecimal[] prediction) {
        if (reality.length != prediction.length) {
            throw new IndexOutOfBoundsException();
        }
        SimpleRegression simpleRegression = new SimpleRegression();
        for (int i = 0; i < reality.length; i++) {
            simpleRegression.addData(reality[i].doubleValue(), prediction[i].doubleValue());
        }
        return new LinearEquation(simpleRegression.getSlope(), simpleRegression.getIntercept());
    }
}
