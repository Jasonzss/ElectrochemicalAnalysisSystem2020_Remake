package com.bluedot.domain.algorithm;

import com.bluedot.domain.algorithm.exception.AlgoInvokeException;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 0:57
 * @Description ：算法类的抽象，各个算法实现类中自定义算法文件的调用方法：execute
 *
 * TODO 算法还需要有获取入参的功能，提示用户在使用这个算法时需要填写哪些参数，以及参数存在哪些限制
 * 比如平滑处理算法存在平滑点数这一参数的设置、滤波处理的算法存在滤波类型、滤波阶数、采样率、截止频率等参数的设置
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
