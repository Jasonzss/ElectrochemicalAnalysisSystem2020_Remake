package com.bluedot.domain.process;

import com.bluedot.domain.algorithm.Algorithm;

import java.util.List;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 1:51
 * @Description ：数据处理的接口
 */
public interface Processor {
    /**
     * 使用指定算法处理数据
     * @param data 待处理的数据
     * @param algoId 算法id
     * @return 处理后的数据
     */
    Double[][] processData(Double[][] data, String algoId);
}
