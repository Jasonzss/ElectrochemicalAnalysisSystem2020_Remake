package com.bluedot.domain.algorithm;

import com.bluedot.domain.algorithm.exception.AlgoInvokeException;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 0:57
 * @Description ：算法类的抽象，各个算法实现类中自定义算法文件的调用方法：execute
 */
public interface Algorithm {
    /**
     * 执行算法
     * @param input 算法的输入
     * @return 算法的输出
     */
    Object execute(Object... input) throws AlgoInvokeException;

    /**
     * 算法可支持的应用场景
     * @param algo_type 算法应用场景类型
     * @return 此算法是否支持该场景的应用
     */
    boolean support(String algo_type);

    /**
     * 获取算法的id
     * @return 算法的id
     */
    String getAlgoId();
}
