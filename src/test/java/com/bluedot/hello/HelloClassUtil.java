package com.bluedot.hello;

import cn.hutool.core.util.ClassUtil;
import com.bluedot.BaseTest;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Jason
 * @creationDate 2023/06/26 - 23:17
 */
public class HelloClassUtil extends BaseTest {
    private static class Algo{
        int a;
        String b;

        public void test(String b, int a){
            System.out.println(b+":"+a);
        }
    }

    @Test
    public void testGetClasses() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int b = 20;
        String a = "aaa";
        Class<?>[] classes = ClassUtil.getClasses(b, a);
        log.info(String.valueOf(classes[0]));
        log.info(String.valueOf(int.class));
        log.info(String.valueOf(Integer.class));
        Method test = Algo.class.getMethod("test", classes[1], int.class);
        test.invoke(new Algo(), a, b);
    }
}
