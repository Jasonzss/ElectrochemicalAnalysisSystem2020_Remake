package com.bluedot.domain.algorithm;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 0:57
 * @Description ：
 */
public interface Algorithm {
    /**
     * 执行算法
     * @param args 算法的输入
     * @return 算法的输出
     */
    Object[] execute(Object... args);

    boolean support();
}
