package com.bluedot.hello;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ByteUtil;
import org.junit.Test;

import java.io.*;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 17:33
 * @Description ：
 */
public class HelloFile {
    File file;

    @Test
    public void testAll() throws IOException {
        file = new File("src/test/resources/algo/java/java_javaFile/JavaAlgorithm.java");

        //src\test\resources\algo\java\java_javaFile\JavaAlgorithm.java
        System.out.println(file.getPath());

        //JavaAlgorithm.java
        System.out.println(file.getName());

        //D:\Git\gitCodes\ElectrochemicalAnalysisSystem2020_Remake\src\test\resources\algo\java\java_javaFile\JavaAlgorithm.java
        System.out.println(file.getAbsolutePath());

        //获取规范路径名
        //D:\Git\gitCodes\ElectrochemicalAnalysisSystem2020_Remake\src\test\resources\algo\java\java_javaFile\JavaAlgorithm.java
        System.out.println(file.getCanonicalPath());

        //src\test\resources\algo\java\java_javaFile
        System.out.println(file.getParent());
    }

    @Test
    public void testUpdateFile() throws IOException {
        File file = new File("C:\\Users\\Lenovo\\Desktop\\test.txt");
        File f = new File("D:\\Git\\gitCodes\\ElectrochemicalAnalysisSystem2020_Remake\\src\\main\\resources\\algo\\java\\test_compile\\JavaAlgorithm.java");

        FileOutputStream fos = new FileOutputStream(file);
        FileInputStream fileInputStream = new FileInputStream(f);

        System.out.println(IoUtil.copy(fileInputStream, fos));

        fileInputStream.close();
        fos.close();
    }
}
