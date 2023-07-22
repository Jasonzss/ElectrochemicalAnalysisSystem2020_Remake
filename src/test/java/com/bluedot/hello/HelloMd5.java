package com.bluedot.hello;

import cn.hutool.crypto.digest.MD5;
import org.junit.Test;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 8:51
 */
public class HelloMd5 {
    @Test
    public void test(){
        String s = MD5.create().digestHex("2418972236");
        System.out.println(s);
    }
}
