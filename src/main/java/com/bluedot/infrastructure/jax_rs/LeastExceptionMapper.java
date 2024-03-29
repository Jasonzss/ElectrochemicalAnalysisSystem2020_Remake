package com.bluedot.infrastructure.jax_rs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 兜底的异常处理器
 *
 * @author Jason
 * @creationDate 2023/07/22 - 19:46
 */
@Provider
public class LeastExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger log = LoggerFactory.getLogger(LeastExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        //正常上线时需要将此处改成响应的异常日志记录
        exception.printStackTrace();
        return Response.status(500).entity("系统出现异常，请稍后访问——"+exception.toString()).type("text/plain").build();
    }
}
