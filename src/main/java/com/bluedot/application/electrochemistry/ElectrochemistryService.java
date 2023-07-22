package com.bluedot.application.electrochemistry;

import cn.hutool.core.collection.CollectionUtil;
import com.bluedot.application.electrochemistry.dto.AnalyzedGraphData;
import com.bluedot.application.electrochemistry.dto.AnalyzedModelReport;
import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.application.electrochemistry.vo.CurveFileProcessTable;
import com.bluedot.application.electrochemistry.vo.CurveProcessTable;
import com.bluedot.application.electrochemistry.vo.ModelAnalysisTable;
import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.domain.algorithm.AlgorithmManager;
import com.bluedot.domain.analysis.AnalysisResult;
import com.bluedot.domain.analysis.Analyst;
import com.bluedot.domain.analysis.model.AnalyzedData;
import com.bluedot.domain.analysis.model.QuantitySet;
import com.bluedot.domain.file.model.ModelAnalysisGraphFile;
import com.bluedot.domain.process.Processor;
import com.bluedot.domain.process.model.Curve;
import com.bluedot.domain.process.model.CurveParameter;
import com.bluedot.domain.process.model.Point;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.exception.CustomException;
import com.bluedot.infrastructure.http.InputStreamResponseHandler;
import com.bluedot.infrastructure.repository.AnalyzedModelReportRepository;
import com.bluedot.infrastructure.repository.CurveDataRepository;
import com.bluedot.infrastructure.utils.*;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.util.Timeout;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Jason
 * @creationDate 2023/07/11 - 16:00
 */
@Service
public class ElectrochemistryService {
    private static final UnitUtil.Unit DEFAULT_CURRENT_UNIT = UnitUtil.Unit.AMP_A;
    private static final UnitUtil.Unit DEFAULT_SOLUBILITY_UNIT = UnitUtil.Unit.MOL_M;
    private final static String PYTHON_WEB_URL = "http://127.0.0.1:5000";
    private final CloseableHttpClient httpclient = HttpClients.createDefault();

    @Inject
    private CurveDataRepository curveDataRepository;

    @Inject
    private AnalyzedModelReportRepository reportRepository;

    @Inject
    private AlgorithmManager manager;

    @Inject
    Processor processor;

    @Inject
    private Analyst analyst;

    public CurveData processFile(CurveFileProcessTable table, String userEmail){
        CurveData curveData = table.generateCurveData();
        curveData.setUser(new User(userEmail));

        readFile(table.getFile(), curveData);

        return curveDataRepository.save(curveData);
    }

    /**
     * 使用指定算法处理波形图点位
     * @param points 波形图的点位
     * @param algoId 算法id
     * @return 处理完后的数据
     */
    public CurveData processCurveWithAlgo(Double[][] points, String algoId, Object... args){
        Double[][] doubles = processor.processData(points, algoId, args);
        CurveParameter waveformFactor = processor.getWaveformFactor(points);

        CurveData data = new CurveData();
        data.setNewestPointsData(getCurveFromDoubleArrays(doubles));
        data.setNewestEp(waveformFactor.getEp());
        data.setNewestIp(waveformFactor.getIp());

        return data;
    }

    private Curve getCurveFromDoubleArrays(Double[][] points) {
        Curve curve = new Curve();
        List<Point> p = new ArrayList<>();
        curve.setPoints(p);

        for (Double[] point : points) {
            p.add(new Point(point[0], point[1]));
        }

        return curve;
    }

    /**
     * 保存数据
     * @return 是否保存成功
     */
    public boolean saveCurveData(CurveProcessTable table){
        CurveData data = table.generateCurveData();
        Optional<CurveData> byId = curveDataRepository.findById(table.getCurveDataId());
        if (byId.isPresent()){
            CurveData d = byId.get();
            PojoUtil.updatePojo(data, d);
            curveDataRepository.save(d);
            return true;
        }
        return false;
    }

    /**
     * 对用户填入的参数进行建模，并对建立出的方程模型进行分析
     */
    public AnalyzedModelReport modelingAnalysis(ModelAnalysisTable table, String userEmail){
        //1. 查询指定的波形数据的波形参数
        List<CurveData> dataList = curveDataRepository.findCurveParameter(table.getDataIds());

        //2. 预处理并将数据分为 训练集 和 测试集
        Algorithm algorithm = manager.getAlgorithm(table.getPreprocessAlgorithmId());
        List<CurveData> preprocessed = (List<CurveData>) algorithm.execute(dataList);
        Map<String, List<CurveData>> sets = divideDataSet(preprocessed);
        List<CurveData> test = sets.get("test");
        List<CurveData> train = sets.get("train");

        //3. 使用训练集进行数据建模得到 线性模型 数据
        LinearEquation model = analyst.getModel(sets.get("train"), table.getModelingAlgoId());

        //4. 将【训练集实际值】和【测试集实际值】的  带入 线性模型 算出【训练集预测值】和【测试集预测值】
        AnalyzedData trainSet = getAnalyzedData(model, train);
        AnalyzedData testSet = getAnalyzedData(model, test);

        //5. 对【测试集的电流、预测溶度、真实溶度】 和 【训练集的电流、预测溶度、真实溶度】进行分析得到对线性模型的评价参数
        AnalysisResult analysisResult = analyst.analysis(trainSet, testSet);

        //6. 使用以上得到的所有数据生成两张分析图片
        AnalyzedGraphData trainGraph = new AnalyzedGraphData(analysisResult.getTrainEquation().toString(),
                analysisResult.getTrainMap(),
                getXYFromRealityAndPrediction(trainSet));

        AnalyzedGraphData testGraph = new AnalyzedGraphData(analysisResult.getTestEquation().toString(),
                analysisResult.getTestMap(),
                getXYFromRealityAndPrediction(testSet));

        //7. 封装并返回分析结果
        AnalyzedModelReport analyzedModelReport = new AnalyzedModelReport();
        AnalyzedModelReport save = reportRepository.save(analyzedModelReport);

        try {
            InputStream testGraphIs = getGraphInputStream(testGraph);
            InputStream trainGraphIs = getGraphInputStream(trainGraph);

            ModelAnalysisGraphFile testFile = new ModelAnalysisGraphFile(save.getReport_id(), true);
            ModelAnalysisGraphFile trainFile = new ModelAnalysisGraphFile(save.getReport_id(), false);

            testFile.updateFile(testGraphIs);
            trainFile.updateFile(trainGraphIs);

            save.setTestSetGraph(testFile.getFilePath());
            save.setTrainingSetGraph(trainFile.getFilePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reportRepository.save(save);
    }

    private Double[][] getXYFromRealityAndPrediction(AnalyzedData data){
        List<BigDecimal> concentration = data.getConcentrationSet().getValues();
        List<BigDecimal> predictedConcentration = data.getPredictedConcentrationSet().getValues();
        Double[][] a = new Double[concentration.size()][2];

        for(int i = 0; i < concentration.size(); i++){
            a[i][0] = concentration.get(i).doubleValue();
            a[i][1] = predictedConcentration.get(i).doubleValue();
        }

        return a;
    }

    private AnalyzedData getAnalyzedData(LinearEquation model, List<CurveData> data){
        List<BigDecimal> currentSet = new ArrayList<>();
        List<BigDecimal> concentrationSet = new ArrayList<>();
        List<BigDecimal> predictedConcentrationSet = new ArrayList<>();
        data.forEach(t -> {
            BigDecimal predicted = model.calculateY(t.getIp().getValue());
            currentSet.add(t.getIp().getValue());
            concentrationSet.add(t.getMaterialSolubility().getValue());
            predictedConcentrationSet.add(predicted);
        });

        return  new AnalyzedData(
                    new QuantitySet(currentSet, DEFAULT_CURRENT_UNIT),
                    new QuantitySet(concentrationSet, DEFAULT_SOLUBILITY_UNIT),
                    new QuantitySet(predictedConcentrationSet, DEFAULT_SOLUBILITY_UNIT)
                );
    }

    /**
     * 读取用户上传的txt文件，将其中的ep、ip、点位数据读取出来并存入波形数据对象中
     * @param fileItem txt文件
     * @param curveData 波形数据
     */
    private void readFile(FileItem fileItem, CurveData curveData){
        try {
            //创建字符输入流读取对象读取传入的文件
            InputStreamReader isr = new InputStreamReader(fileItem.getInputStream(),"GBK");
            BufferedReader br = new BufferedReader(isr);

            //将所有的点位数据读取到CurveData中
            List<Point> points = new ArrayList<>();
            BigDecimal ep = null;
            BigDecimal ip = null;

            //先读取第一行
            String line = br.readLine();
            boolean flag = false;

            while(!flag){
                if(!StringUtils.isEmpty(line)){
                    if(line.startsWith("Ep = ")){
                        line = line.trim();
                        ep = new BigDecimal(line.substring(line.indexOf("Ep = ")+5, line.length()-1));
                    } else if(line.startsWith("ip = ")){
                        line = line.trim();
                        ip = new BigDecimal(line.substring(line.indexOf("ip = ")+5, line.length()-1));
                        flag = true;
                    }
                }

                line = br.readLine();
            }

            line = br.readLine();
            flag = false;
            while (line != null){
                if(flag){
                    line = line.trim();
                    if (StringUtils.isNoneBlank(line)){
                        String[] split = line.split(", ");
                        points.add(new Point(new BigDecimal(split[0]), new BigDecimal(split[1])));
                    }
                } else if (line.trim().contains("Potential/V, Current/A")){
                    flag = true;
                }

                //不断读取每一行
                line = br.readLine();
            }

            //将电压电流放入ExpData中
            if (CollectionUtil.isEmpty(points)){
                throw new CustomException(CommonErrorCode.E_4002);
            }

            Curve curve = new Curve();
            curve.setPoints(points);

            curveData.setOriginalPointsData(curve);
            curveData.setOriginalEp(new Quantity(ep, UnitUtil.Unit.VOL_V));
            curveData.setOriginalIp(new Quantity(ip, UnitUtil.Unit.AMP_A));
        } catch (NumberFormatException e) {
            throw new CustomException(CommonErrorCode.E_4004);
        } catch (IOException e) {
            throw new CustomException(CommonErrorCode.E_4003);
        }
    }

    /**
     * @param data 需要划分的数据集
     * @return 返回一个Map，属性【train】表示分割好的训练集，属性【test】表示分割好的测试集
     */
    private Map<String, List<CurveData>> divideDataSet(List<CurveData> data) {
        if (data == null || data.size() == 0) {
            //参数不能为空
            return null;
        }
        //返回变量
        Map<String, List<CurveData>> ret = new HashMap<>(2);
        //训练集
        List<CurveData> train = new ArrayList<>();
        //测试集
        List<CurveData> test = new ArrayList<>();
        //得到0-data.length的随机序列集
        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < data.size(); i ++) {
            index.add(i);
        }
        Collections.shuffle(index);

        //将前80%作为训练集，后20%作为测试集。
        double ratio = 0.8;
        int splitPoint = (int) (data.size() * ratio);

        for(int i : index){
            if(i < splitPoint){
                train.add(data.get(i));
            }else {
                test.add(data.get(i));
            }
        }

        ret.put("train", train);
        ret.put("test", test);

        return ret;
    }

    private InputStream getGraphInputStream(AnalyzedGraphData graphData) throws IOException {
        Gson gson = new Gson();
        //初始化Post请求
        HttpPost post = new HttpPost(PYTHON_WEB_URL+"/graph");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.of(10000, TimeUnit.MILLISECONDS))
                .build();
        post.setConfig(requestConfig);
        //表示客户端发送给服务器端的数据格式
        post.setHeader("Content-Type", "multipart/form-data; charset=UTF-8; boundary=mVeFwaGv8_V9nTHRREq6Aw0oksC66x");
        post.setHeader("Accept", "application/json;charset=UTF-8");

        HttpEntity entity = MultipartEntityBuilder.create()
                .setCharset(StandardCharsets.UTF_8)
                .addTextBody("equation", graphData.getEquation())
                .addTextBody("params", gson.toJson(graphData.getParams()))
                .addTextBody("points", gson.toJson(graphData.getPoints()))
                .addTextBody("xUnit", graphData.getxUnit())
                .addTextBody("yUnit", graphData.getyUnit())
                .build();

        post.setEntity(entity);

        return httpclient.execute(post, new InputStreamResponseHandler());
    }

    public CurveDataRepository getCurveDataRepository() {
        return curveDataRepository;
    }

    public void setCurveDataRepository(CurveDataRepository curveDataRepository) {
        this.curveDataRepository = curveDataRepository;
    }
}
