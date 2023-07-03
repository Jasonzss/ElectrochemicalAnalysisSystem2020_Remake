package com.bluedot.domain.file.model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * @author Jason
 * @creationDate 2023/06/27 - 14:54
 */
public class ClassFileTest {
    ClassFile cls;

    @Before
    public void testCheckUpdateClass(){
        JavaFile file = new JavaFile("java_test");
        file.setFile(new File("src\\main\\resources\\algo\\java\\java_test\\JavaAlgorithm.java"));
        ClassFile compile = file.compile();
    }
}
