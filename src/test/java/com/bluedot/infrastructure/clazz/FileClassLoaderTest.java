package com.bluedot.infrastructure.clazz;

import com.bluedot.BaseTest;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 11:11
 * @Description ：
 */
public class FileClassLoaderTest extends BaseTest {
    @Test
    public void testLoadClass() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException {
        FileClassLoader fileClassLoader = new FileClassLoader();
        fileClassLoader.setClassFile("src\\main\\resources\\algo\\java\\java_test\\JavaAlgorithm.class");
        Object obj = null;
        obj = fileClassLoader.loadClass("algo.java.java_test.JavaAlgorithm").newInstance();
        log.info(String.valueOf(obj.getClass()));
        Method m = obj.getClass().getMethod("hello");
        m.invoke(obj);
    }

    @Test(expected = ClassNotFoundException.class)
    public void testLoadClass_ClassNotFoundException() throws ClassNotFoundException{
        FileClassLoader fileClassLoader = new FileClassLoader();
        fileClassLoader.setClassFile("NotExist.class");
        Class<?> notExist = fileClassLoader.loadClass("NotExist");
    }
}
