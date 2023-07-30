package com.bluedot.hello;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import com.bluedot.BaseTest;
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
public class HelloHutool extends BaseTest {
    private static class User{
        private Integer a;
        private String b;

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        @Override
        public String toString() {
            return "User{" +
                    "a=" + a +
                    ", b='" + b + '\'' +
                    '}';
        }
    }

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

    @Test
    public void testBeanUtil1(){
        //源对象
        User u1 = new User();
        //目标对象
        User u2 = new User();

        u1.setA(1);
        u1.setB(null);

        u2.setA(2);
        u2.setB("User2的属性");

        BeanUtil.copyProperties(u1, u2);

        log.info(u1.toString());    //User{a=1, b='null'}
        log.info(u2.toString());    //User{a=1, b='null'} 源对象的所有字段都将覆盖目标对象，包括null
    }

    @Test
    public void testBeanUtil2(){
        //源对象
        User u1 = new User();
        //目标对象
        User u2 = new User();

        u1.setA(1);
        u1.setB(null);

        u2.setA(2);
        u2.setB("User2的属性");

        BeanUtil.copyProperties(u1, u2, CopyOptions.create(User.class, true));

        log.info(u1.toString());    //User{a=1, b='null'}
        log.info(u2.toString());    //User{a=1, b='User2的属性'} 源对象中只有非null的字段才会去覆盖目标对象的字段
    }
}
