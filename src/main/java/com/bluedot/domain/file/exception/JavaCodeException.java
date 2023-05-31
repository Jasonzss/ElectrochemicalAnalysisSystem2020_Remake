package com.bluedot.domain.file.exception;

import com.bluedot.domain.file.FileSystemException;

/**
 * @Author Jason
 * @CreationDate 2023/05/31 - 1:17
 * @Description ：
 */
public class JavaCodeException extends FileSystemException {

    public JavaCodeException(String message) {
        super(message);
    }

    public JavaCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JavaCodeException(Throwable cause) {
        super(cause);
    }

    public JavaCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
