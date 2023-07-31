package com.bluedot.infrastructure.date;

import com.bluedot.infrastructure.exception.CustomException;
import com.bluedot.infrastructure.exception.ErrorCode;

/**
 * @author Jason
 * @since 2023/07/31 - 18:54
 */
public class DateException extends CustomException {
    public DateException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DateException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public DateException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public DateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
