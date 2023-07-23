package com.bluedot.infrastructure.jax_rs.exception_mapper;

import com.bluedot.infrastructure.exception.CustomException;
import com.bluedot.infrastructure.exception.ErrorCode;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Jason
 * @creationDate 2023/07/22 - 19:40
 */
@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {
    @Override
    public Response toResponse(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return Response.status(errorCode.getHttpCode()).entity("错误代码_"+errorCode.getCode()+" : "+errorCode.getMsg()).type("text/plain").build();
    }
}
