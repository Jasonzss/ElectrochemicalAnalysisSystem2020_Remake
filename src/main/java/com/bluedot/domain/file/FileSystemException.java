package com.bluedot.domain.file;

import com.bluedot.infrastructure.exception.CustomException;
import com.bluedot.infrastructure.exception.ErrorCode;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 22:31
 * @Description ：
 */
public class FileSystemException extends CustomException {
    public FileSystemException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FileSystemException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public FileSystemException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public FileSystemException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public FileSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
