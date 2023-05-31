package com.bluedot.hello;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileReader;
import org.junit.Test;

import java.io.File;
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
        File file = new File("src/test/resources/algo/java/java_javaFile/JavaFile.java");

        FileReader reader = new FileReader(file);
        List<String> list = reader.readLines();
        for(String l : list){
            System.out.println(l);
        }

    }
}
