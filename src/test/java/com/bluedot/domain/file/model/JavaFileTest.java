package com.bluedot.domain.file.model;

import cn.hutool.core.io.IoUtil;
import com.bluedot.BaseTest;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 22:20
 * @Description ：
 */
public class JavaFileTest extends BaseTest {
    JavaFile file1;
    JavaFile file3;

    @Before
    public void newJavaFile() {
        file1 = new JavaFile("java_test");
        file3 = new JavaFile("java_test_exception");
    }

    @Test
    public void testReadFile() throws FileNotFoundException {
        IoUtil.readLines(file1.getInputStream(), StandardCharsets.UTF_8, new ArrayList<>())
                .forEach(System.out::println);
    }

    //TODO 异常检查待完成
    @Test
    public void testCheckSourceCode_JavaCodeException() {
        file3.checkSourceCode();
    }
}
