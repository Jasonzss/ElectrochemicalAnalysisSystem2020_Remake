package com.bluedot.domain.process;

import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.domain.algorithm.AlgorithmManager;
import com.bluedot.domain.process.model.CurveParameter;
import com.bluedot.infrastructure.exception.CommonErrorCode;

import javax.inject.Inject;

/**
 * @author Jason
 * @creationDate 2023/07/05 - 22:58
 */
public class SimpleProcessor implements Processor{
    @Inject
    AlgorithmManager manager;

    @Override
    public Double[][] processData(Double[][] data, String algoId, Object... algoParams) {
        try{
            Algorithm algorithm = manager.getAlgorithm(algoId);
            //TODO 算法类方法的输入和输出都是Object，这就使得算法模块充满了不确定性，且使用起来会留下许多坑
            return (Double[][]) algorithm.execute(data, algoParams);
        }catch (ClassCastException e){
            throw new ProcessException(CommonErrorCode.E_4001);
        }
    }

    @Override
    public CurveParameter getWaveformFactor(Double[][] points) {
        int pointsNum = points[0].length;
        Double[] potential = new Double[pointsNum];
        Double[] current = new Double[pointsNum];

        for (int i = 0; i < pointsNum; i++) {
            potential[i] = points[i][0];
            current[i] = points[i][1];
        }

        return curveAnalysis(potential, current);
    }

    /**
     * 波形分析，根据给出的电压电流点位数据确定一条曲线，对此曲线进行波形分析
     * 沿用第一版的计算方法，有略微修改
     * @param potential 波形图的横坐标集合
     * @param current 波形图的纵坐标集合
     * @return 波形图计算出的参数
     */
    private static CurveParameter curveAnalysis(Double[] potential, Double[] current) {
        if (potential.length != current.length) {
            throw new IndexOutOfBoundsException();
        }
        Double[] res = new Double[2];
        int peak = potential.length / 2;
        int left = 0, right = potential.length - 1;
        if (current[0] > 0) {
            while ((peak + 1) <= potential.length - 1 && (peak - 1) >= 0 && !(current[peak] >= current[peak - 1] && current[peak] >= current[peak + 1])) {
                if ((peak + 1) <= potential.length - 1 && (peak - 1) >= 0 && current[peak] > current[peak + 1] && current[peak] < current[peak - 1]) {
                    peak--;
                } else if ((peak + 1) <= potential.length - 1 && (peak - 1) >= 0 && current[peak] > current[peak - 1] && current[peak] < current[peak + 1]) {
                    peak++;
                }
            }
            while (left < peak) {
                if (current[left] > current[left + 1]) {
                    left++;
                } else {
                    break;
                }
            }
            while (peak < right) {
                if (current[right] > current[right - 1]) {
                    right--;
                } else {
                    break;
                }
            }
        } else {
            while ((peak + 1) <= potential.length - 1 && (peak - 1) >= 0 && !(current[peak] <= current[peak - 1] && current[peak] <= current[peak + 1])) {
                if ((peak + 1) <= potential.length - 1 && (peak - 1) >= 0 && current[peak] > current[peak - 1] && current[peak] < current[peak + 1]) {
                    peak--;
                } else if ((peak + 1) <= potential.length - 1 && (peak - 1) >= 0 && current[peak] < current[peak - 1] && current[peak] > current[peak + 1]) {
                    peak++;
                }
            }
            while (left < peak) {
                if (current[left] < current[left + 1]) {
                    left++;
                } else {
                    break;
                }
            }
            while (peak < right) {
                if (current[right] < current[right - 1]) {
                    right--;
                } else {
                    break;
                }
            }
        }

        double k = (current[right] - current[left]) / (potential[right] - potential[left]);
        double b = current[left] - k * potential[left];
        double y = k * potential[peak] + b;

        return new CurveParameter(current[peak] - y, potential[peak]);
    }
}
