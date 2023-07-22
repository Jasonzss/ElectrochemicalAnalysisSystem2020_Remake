package com.bluedot.domain.rbac.exception;

import com.bluedot.infrastructure.exception.CustomException;
import com.bluedot.infrastructure.exception.ErrorCode;

/**
 * @author Jason
 * @creationDate 2023/07/21 - 16:48
 */
public class UserException extends CustomException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UserException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public UserException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
