package com.bluedot.infrastructure.exception;

import org.apache.http.HttpStatus;

/**
 * @author FireRain
 * @version 1.0
 * @date 2022/8/17 11:47
 * 用户异常信息枚举类
 * TODO 各个领域的异常全部放于一个类里是否合理？可能会有以下问题
 *
 * 可能会出现跨模块使用错误的ErrorCode情况
 * 过多的异常挤在一块让人眼花缭乱
 * 所有模块都与这个异常耦合，无法独立出来
 *
 * 所以这里应该有更多种类的ErrorCode
 */
public enum CommonErrorCode implements ErrorCode {

    /**
     * 用户模块异常:E_1xxx
     */
    E_1001(1001, HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION, "登录失败"),

    /**
     * 文件模块异常：E_2xxx
     */
    E_2001(2001,HttpStatus.SC_INTERNAL_SERVER_ERROR,"文件模块异常"),
    E_2002(2002,HttpStatus.SC_INTERNAL_SERVER_ERROR,"找不到目标文件"),
    E_2003(2003,HttpStatus.SC_INTERNAL_SERVER_ERROR,"create file failed,the path is invalid"),
    E_2004(2004,HttpStatus.SC_INTERNAL_SERVER_ERROR,"data output to file failed due to the missing file"),
    E_2005(2005,HttpStatus.SC_INTERNAL_SERVER_ERROR,"data output to file failed"),
    E_2006(2006,HttpStatus.SC_INTERNAL_SERVER_ERROR,"创建业务模型文件失败"),
    E_2007(2007,HttpStatus.SC_INTERNAL_SERVER_ERROR,"目标文件丢失"),
    E_2008(2008,HttpStatus.SC_INTERNAL_SERVER_ERROR,"错误的文件id无法找到匹配的文件类型"),
    E_2009(2009,HttpStatus.SC_INTERNAL_SERVER_ERROR,"创建文件失败"),
    E_2010(2009,HttpStatus.SC_INTERNAL_SERVER_ERROR,"文件系统出现异常"),

    /**
     * 算法模块异常
     */
    E_3001(3001,HttpStatus.SC_INTERNAL_SERVER_ERROR, "算法模块异常"),
    E_3002(3002,HttpStatus.SC_INTERNAL_SERVER_ERROR, "the code has a syntax err"),
    E_3003(3003,HttpStatus.SC_INTERNAL_SERVER_ERROR, "calls to sensitive methods are not allowed"),
    E_3004(3004,HttpStatus.SC_INTERNAL_SERVER_ERROR, "算法构建失败，算法文件已删除或丢失"),
    E_3005(3005,HttpStatus.SC_INTERNAL_SERVER_ERROR, "Java文件内找不到目标Class"),
    E_3006(3006,HttpStatus.SC_INTERNAL_SERVER_ERROR, "该算法不存在"),
    E_3007(3007,HttpStatus.SC_INTERNAL_SERVER_ERROR, "can't find the public algo method matched the input parameter"),
    E_3008(3008,HttpStatus.SC_INTERNAL_SERVER_ERROR, "the target algo method is not public"),
    E_3009(3009,HttpStatus.SC_INTERNAL_SERVER_ERROR, "an exception occurred when calling the target method"),
    E_3010(3010,HttpStatus.SC_INTERNAL_SERVER_ERROR, "fail to instant algo class"),
    E_3011(3011,HttpStatus.SC_INTERNAL_SERVER_ERROR, "错误的输入参数导致无法调用指定算法"),
    E_3012(3012,HttpStatus.SC_INTERNAL_SERVER_ERROR, "Http请求发送失败"),
    E_3013(3013,HttpStatus.SC_INTERNAL_SERVER_ERROR, "未指定算法类型的算法类"),
    E_3014(3014,HttpStatus.SC_INTERNAL_SERVER_ERROR, "该Java算法文件未指定算法方法"),
    E_3015(3015,HttpStatus.SC_INTERNAL_SERVER_ERROR, "用于Java算法名称指定的字段不公开导致无法取得"),
    E_3016(3016,HttpStatus.SC_INTERNAL_SERVER_ERROR, "Java算法调用失败"),
    E_3017(3017,HttpStatus.SC_INTERNAL_SERVER_ERROR, "找不到Java算法指定的方法"),
    E_3018(3018,HttpStatus.SC_INTERNAL_SERVER_ERROR, "算法已过期或尚未初始化"),
    E_3019(3019,HttpStatus.SC_INTERNAL_SERVER_ERROR, "算法对象构建失败，请检查算法构造器"),
    E_3020(3020,HttpStatus.SC_INTERNAL_SERVER_ERROR, "远程调用python算法失败"),

    /**
     * 波形处理模块异常
     */
    E_4001(4001,HttpStatus.SC_INTERNAL_SERVER_ERROR, "执行数据处理算法失败"),
    E_4002(4002,HttpStatus.SC_BAD_REQUEST, "无法解析文件内容"),
    E_4003(4003,HttpStatus.SC_BAD_REQUEST, "文件出现异常"),
    E_4004(4004,HttpStatus.SC_BAD_REQUEST, "文件内容格式异常导致无法正常读取"),

    /**
     * 持久层异常
     */
    E_5001(4004,HttpStatus.SC_BAD_REQUEST, "解/压缩数据失败"),

    ;

    private final int code;
    private final int httpCode;
    private String msg;

    CommonErrorCode(int code, int httpCode, String msg) {
        this.code = code;
        this.httpCode = httpCode;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public int getHttpCode() {
        return httpCode;
    }

    @Override
    public CommonErrorCode setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
