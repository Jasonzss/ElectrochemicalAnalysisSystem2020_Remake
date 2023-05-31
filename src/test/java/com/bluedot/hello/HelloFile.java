package com.bluedot.hello;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 17:33
 * @Description ：
 */
public class HelloFile {
    File file;

    @Test
    public void testAll() throws IOException {
        file = new File("src/test/resources/algo/java/java_javaFile/JavaFile.java");

        //src\test\resources\algo\java\java_javaFile\JavaFile.java
        System.out.println(file.getPath());

        //JavaFile.java
        System.out.println(file.getName());

        //D:\Git\gitCodes\ElectrochemicalAnalysisSystem2020_Remake\src\test\resources\algo\java\java_javaFile\JavaFile.java
        System.out.println(file.getAbsolutePath());

        //获取规范路径名
        //D:\Git\gitCodes\ElectrochemicalAnalysisSystem2020_Remake\src\test\resources\algo\java\java_javaFile\JavaFile.java
        System.out.println(file.getCanonicalPath());

        //src\test\resources\algo\java\java_javaFile
        System.out.println(file.getParent());
    }
}
