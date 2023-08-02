package com.bluedot.infrastructure.utils;

/**
 * @author Jason
 * @since 2023/08/01 - 20:04
 */
public class TestUtil {
    private static Boolean isRunningTest = null;

    /**
     * 检测是否在运行单元测试
     */
    public static Boolean isTest() {
        if (null == isRunningTest) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

            for (StackTraceElement stackTraceElement : stackTrace) {
                String stackString = stackTraceElement.toString();
                if (stackString.lastIndexOf("junit.runners") > -1) {
                    isRunningTest = true;
                    return isRunningTest;
                }
            }
            isRunningTest = false;
        }

        return isRunningTest;
    }
}
