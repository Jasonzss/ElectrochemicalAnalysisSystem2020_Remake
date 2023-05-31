package com.bluedot.domain.algorithm;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 0:57
 * @Description ：
 */
public interface Algorithm {
    /**
     * 执行算法
     * @param input 算法的输入
     * @return 算法的输出
     */
    Object execute(Object... input) throws AlgoInvokeException;

    /**
     * 判断是否支持对输入参数的计算
     * @param input 算法的输入
     * @return 是否支持对此输入的计算
     */
    boolean support(Object... input);

    /**
     * 获取算法的id
     * @return 算法的id
     */
    String getAlgoId();
}
