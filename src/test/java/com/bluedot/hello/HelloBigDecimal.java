package com.bluedot.hello;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author Jason
 * @creationDate 2023/07/13 - 20:52
 */
public class HelloBigDecimal {
    @Test
    public void test(){
        String a = "-2.882e-6";
        BigDecimal bigDecimal = new BigDecimal(a);
        System.out.println(bigDecimal);
    }
}
