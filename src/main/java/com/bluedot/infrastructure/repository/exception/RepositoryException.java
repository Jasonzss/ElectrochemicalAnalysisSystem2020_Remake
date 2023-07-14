package com.bluedot.infrastructure.repository.exception;

import com.bluedot.infrastructure.exception.CustomException;
import com.bluedot.infrastructure.exception.ErrorCode;

/**
 * @author Jason
 * @creationDate 2023/07/14 - 14:29
 */
public class RepositoryException extends CustomException {
    public RepositoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public RepositoryException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public RepositoryException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public RepositoryException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public RepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
