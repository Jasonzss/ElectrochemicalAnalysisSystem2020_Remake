package com.bluedot.application;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author Jason
 * @creationDate 2023/07/22 - 20:21
 */
@Singleton
@Component
public class CaptchaDiagramService implements FactoryBean<CaptchaDiagramService> {
    /**
     * 字体只显示大写，去掉了1,0,I,O几个容易混淆的字符
     */
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    public static final Integer CODE_LENGTH = 6;

    /**
     * 验证码集合：key为设置在客户端的cookie，用于唯一标识；value是对应客户端的验证码
     */
    private static final Map<UUID,String> captchaCodes = new HashMap<>();

    /**
     * 通过验证的设备的cookie
     */
    Set<UUID> captchaPassed = new HashSet<>();

    public BufferedImage getCaptchaDiagram(UUID uuid){
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(220, 60);
        captchaCodes.put(uuid, generateVerifyCode());
        return lineCaptcha.getImage();
    }

    public boolean verifyCode(String code, String uuid){
        UUID id = UUID.fromString(uuid);
        String s = captchaCodes.get(id);

        if(code != null && code.equalsIgnoreCase(s)){
            captchaCodes.remove(id);
            captchaPassed.add(id);
            return true;
        }
        return false;
    }

    public boolean isCaptchaPassed(String uuid){
        UUID id = UUID.fromString(uuid);

        if (captchaPassed.contains(id)) {
            captchaPassed.remove(id);
            return true;
        }

        return false;
    }

    /**
     * 使用指定源生成验证码
     * @return 返回指定长度和字符的验证码字符串
     */
    private static String generateVerifyCode() {
        int codesLen = VERIFY_CODES.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            verifyCode.append(VERIFY_CODES.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * 仅供测试使用
     *
     * @param uuid 验证码对应的唯一id
     * @return 验证码
     */
    public String getVerifyCode(String uuid){
        return captchaCodes.get(UUID.fromString(uuid));
    }


    //-----------------------FactoryBean实现--------------------------------

    @Override
    public CaptchaDiagramService getObject() throws Exception {
        return getCaptchaDiagramService();
    }

    @Override
    public Class<?> getObjectType() {
        return CaptchaDiagramService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    //-----------------------实现单例模式--------------------------------

    private static class CaptchaDiagramServiceHolder {
        private static final CaptchaDiagramService INSTANCE = new CaptchaDiagramService();
    }

    private CaptchaDiagramService (){}

    public static final CaptchaDiagramService getCaptchaDiagramService() {
        return CaptchaDiagramServiceHolder.INSTANCE;
    }
}
