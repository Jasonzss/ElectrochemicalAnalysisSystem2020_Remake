package com.bluedot.domain.process;

import com.bluedot.domain.process.model.CurveParameter;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 1:51
 * @Description ：数据处理的接口
 */
public interface Processor {
    /**
     * 使用指定算法处理点位数据
     * @param data 待处理的点位数据
     * @param algoId 算法id
     * @param algoParams 调用算法的参数
     * @return 处理后的数据
     */
    Double[][] processData(Double[][] data, String algoId, Object... algoParams);

    /**
     * 分析处理后的波形图，得到波形参数
     * @param data 波形图点位数据
     * @return 波形参数
     */
    CurveParameter getWaveformFactor(Double[][] data);
}
