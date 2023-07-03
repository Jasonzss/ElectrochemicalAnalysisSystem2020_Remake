package com.bluedot.infrastructure.exception;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 17:01
 * @Description ：
 * TODO 对于一些需要定制参数的异常还没有进行扩展
 */
public class CustomException extends RuntimeException{
    /**
     * 附加自定义的错误信息到异常中
     */
    private ErrorCode errorCode;

    //ErrorCode属性的get&set方法

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    //五种本异常类的构造方法

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public CustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CustomException(Throwable cause, ErrorCode errorCode) {
        super(errorCode.getMsg(), cause);
        this.errorCode = errorCode;
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}
