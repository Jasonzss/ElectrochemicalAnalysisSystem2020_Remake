package com.bluedot.domain.analysis;

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
     * @param modelAlgorithm 建模算法
     * @return 构建好的模型
     */
    Model getModel(List<String> ExpDataIds, String modelAlgorithm);

    /**
     * 对模型进行分析、评价
     * @param model 模型
     * @return 分析结果
     */
    AnalysisResult analysis(Model model);
}
