package com.bluedot.hello;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 22:01
 * @Description ：
 */
public class HelloHutool {
    @Test
    public void testDateUtil(){
        long timeMillis = System.currentTimeMillis();
        System.out.println(DateUtil.date(timeMillis));
    }

    @Test
    public void testFileReader(){
        File file = new File("src\\main\\resources\\algo\\java\\java_test\\JavaAlgorithm.java");

        FileReader reader = new FileReader(file);
        List<String> list = reader.readLines();
        for(String l : list){
            System.out.println(l);
        }

    }

    @Test
    public void testIoUtil() throws FileNotFoundException {
        File file = new File("src\\test\\resources\\test_file\\JavaAlgorithm.java");

        File file1 = new File("src\\test\\resources\\algo\\java\\JavaAlgorithm.java");
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(file1);
        IoUtil.write(fos, StandardCharsets.UTF_8, false, "package com.java;\n\n");
        IoUtil.copy(fis, fos);
        file1.deleteOnExit();
    }

    @Test
    public void testIoUtil1() throws IOException {
        File file = new File("src\\test\\resources\\test_file\\JavaAlgorithm.java");
        File file2 = new File("src\\test\\resources\\test_file\\Package.java");
        File file1 = new File("src\\test\\resources\\algo\\java\\JavaAlgorithm.java");

        FileInputStream fis = new FileInputStream(file);
        FileInputStream fis1 = new FileInputStream(file2);
        SequenceInputStream sis = new SequenceInputStream(fis1, fis);
        FileOutputStream fos = new FileOutputStream(file1);

        IoUtil.copy(sis, fos);
        fos.close();
        file1.deleteOnExit();
    }
}
