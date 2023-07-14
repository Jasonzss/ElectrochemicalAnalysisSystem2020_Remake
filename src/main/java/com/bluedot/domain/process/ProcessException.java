package com.bluedot.domain.process;

import com.bluedot.infrastructure.exception.CustomException;
import com.bluedot.infrastructure.exception.ErrorCode;

/**
 * @author Jason
 * @creationDate 2023/07/11 - 15:32
 */
public class ProcessException extends CustomException {
    public ProcessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ProcessException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ProcessException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public ProcessException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public ProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
