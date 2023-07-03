package com.bluedot.hello;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 8:33
 * @Description ：对JavaCompiler的测试使用
 */
public class HelloJavaCompiler {
    private static final Logger log = LoggerFactory.getLogger(HelloJavaCompiler.class);

    @Test
    public void testRun() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, "src/test/resources/algo/java/java_javaFile/JavaAlgorithm.java");
        log.debug(result == 0 ? "编译成功" : "编译失败");
    }

    @Test
    public void testRun_NotExistJavaFile() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, "D:\\NotExist.java");
        log.debug(result == 0 ? "编译成功" : "编译失败");
    }
}
