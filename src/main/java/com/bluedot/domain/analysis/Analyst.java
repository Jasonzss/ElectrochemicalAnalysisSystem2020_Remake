package com.bluedot.domain.analysis;

import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.domain.analysis.model.AnalyzedData;
import com.bluedot.infrastructure.utils.LinearEquation;

import java.util.List;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 1:52
 * @Description ：
 */
public interface Analyst {
    /**
     * 对选取的数据使用指定算法进行建模
     * @param ExpDataIds 数据集
     * @param modelAlgorithmId 建模算法的id
     * @return 构建好的模型
     */
    LinearEquation getModel(List<CurveData> ExpDataIds, String modelAlgorithmId);

    /**
     * 对模型产生的数据进行分析，得到模型的分析参数
     * @param train 训练集
     * @param test 测试集
     * @return 模型分析结果
     */
    AnalysisResult analysis(AnalyzedData train, AnalyzedData test);
}
