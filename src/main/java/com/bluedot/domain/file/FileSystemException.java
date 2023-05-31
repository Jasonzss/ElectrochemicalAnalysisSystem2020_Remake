package com.bluedot.domain.file;

import com.bluedot.infrastructure.exception.DomainException;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 22:31
 * @Description ：
 */
public class FileSystemException extends DomainException {
    public FileSystemException(String message) {
        super(message);
    }

    public FileSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSystemException(Throwable cause) {
        super(cause);
    }

    public FileSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
