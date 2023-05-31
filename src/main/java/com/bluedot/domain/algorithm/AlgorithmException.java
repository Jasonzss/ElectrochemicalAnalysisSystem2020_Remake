package com.bluedot.domain.algorithm;

import com.bluedot.infrastructure.exception.DomainException;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 16:31
 * @Description ：
 */
public class AlgorithmException extends DomainException {

    public AlgorithmException(String message) {
        super(message);
    }

    public AlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlgorithmException(Throwable cause) {
        super(cause);
    }

    public AlgorithmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
