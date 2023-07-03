package com.bluedot.domain.algorithm.exception;

import com.bluedot.infrastructure.exception.CustomException;
import com.bluedot.infrastructure.exception.ErrorCode;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 16:31
 * @Description ：
 */
public class AlgorithmException extends CustomException {
    public AlgorithmException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlgorithmException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public AlgorithmException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public AlgorithmException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public AlgorithmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
