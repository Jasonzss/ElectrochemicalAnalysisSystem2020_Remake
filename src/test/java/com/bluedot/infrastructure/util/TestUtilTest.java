package com.bluedot.infrastructure.util;

import com.bluedot.infrastructure.utils.TestUtil;
import org.junit.Test;

/**
 * @author Jason
 * @since 2023/08/01 - 20:07
 */
public class TestUtilTest {
    @Test
    public void test() {
        //true
        System.out.println(TestUtil.isTest());
    }

    public static void main(String[] args) {
        //false
        System.out.println(TestUtil.isTest());
    }
}
