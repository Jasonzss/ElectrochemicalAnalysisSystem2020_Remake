package com.bluedot.domain.algorithm.python;

import com.bluedot.domain.algorithm.exception.AlgoInvokeException;
import com.bluedot.infrastructure.exception.ErrorCode;

/**
 * @author Jason
 * @creationDate 2023/07/02 - 21:45
 */
public class PythonInvokeException extends AlgoInvokeException {
    public PythonInvokeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PythonInvokeException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public PythonInvokeException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public PythonInvokeException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public PythonInvokeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
