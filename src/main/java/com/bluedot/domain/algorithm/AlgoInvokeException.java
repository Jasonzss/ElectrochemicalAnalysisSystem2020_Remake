package com.bluedot.domain.algorithm;

import com.bluedot.infrastructure.exception.DomainException;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 15:33
 * @Description ：
 */
public class AlgoInvokeException extends AlgorithmException {
    public AlgoInvokeException(String message) {
        super(message);
    }

    public AlgoInvokeException(String message, Throwable cause) {
        super(message, cause);
    }
}
