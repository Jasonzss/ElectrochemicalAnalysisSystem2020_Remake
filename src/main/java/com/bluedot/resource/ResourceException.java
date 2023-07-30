package com.bluedot.resource;

import com.bluedot.infrastructure.exception.CustomException;
import com.bluedot.infrastructure.exception.ErrorCode;

import java.util.function.Supplier;

/**
 * @author Jason
 * @since 2023/07/30 - 21:47
 */
public class ResourceException extends CustomException implements Supplier<ResourceException> {
    public ResourceException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ResourceException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public ResourceException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public ResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

    @Override
    public ResourceException get() {
        return this;
    }
}
