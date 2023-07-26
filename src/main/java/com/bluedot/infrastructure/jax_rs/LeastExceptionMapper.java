package com.bluedot.infrastructure.jax_rs;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Jason
 * @creationDate 2023/07/22 - 19:46
 */
@Provider
public class LeastExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        return Response.status(500).entity("系统出现异常，请稍后访问："+exception.getMessage()).type("text/plain").build();
    }
}
