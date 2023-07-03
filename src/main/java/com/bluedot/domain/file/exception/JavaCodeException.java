package com.bluedot.domain.file.exception;

import com.bluedot.domain.file.FileSystemException;
import com.bluedot.infrastructure.exception.ErrorCode;

/**
 * @Author Jason
 * @CreationDate 2023/05/31 - 1:17
 * @Description ：
 */
public class JavaCodeException extends FileSystemException {
    public JavaCodeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public JavaCodeException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public JavaCodeException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public JavaCodeException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public JavaCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
