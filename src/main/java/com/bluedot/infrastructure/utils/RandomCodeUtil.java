package com.bluedot.infrastructure.utils;

import java.util.Random;

/**
 * @author Jason
 * @since 2023/07/31 - 15:47
 */
public class RandomCodeUtil {
    /**
     * 字体只显示大写，去掉了1,0,I,O几个容易混淆的字符
     */
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    public static final Integer CODE_LENGTH = 6;

    /**
     * 使用指定源生成验证码
     * @return 返回指定长度和字符的验证码字符串
     */
    public static String generateVerifyCode(int codeLength, String source) {
        int codesLen = source.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            verifyCode.append(source.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * 使用默认配置生成验证码
     * @return 返回默认长度和字符的验证码字符串
     */
    public static String generateVerifyCode() {
        return generateVerifyCode(CODE_LENGTH, VERIFY_CODES);
    }
}
