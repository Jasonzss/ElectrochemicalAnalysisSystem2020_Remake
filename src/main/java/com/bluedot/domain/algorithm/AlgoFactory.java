package com.bluedot.domain.algorithm;

import com.bluedot.domain.algorithm.exception.AlgorithmException;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 16:48
 * @Description ：算法工厂的顶级抽象
 *
 * 协调file模块生成algo对象
 */
public interface AlgoFactory {
    /**
     * 根据算法id获取算法
     * @param algoId 算法id
     * @return 算法
     */
    Algorithm getAlgorithm(String algoId) throws AlgorithmException;
}
