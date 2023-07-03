package com.bluedot.domain.algorithm.exception;

import com.bluedot.infrastructure.exception.ErrorCode;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 15:33
 * @Description ：
 */
public class AlgoInvokeException extends AlgorithmException {
    public AlgoInvokeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlgoInvokeException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public AlgoInvokeException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public AlgoInvokeException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public AlgoInvokeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
