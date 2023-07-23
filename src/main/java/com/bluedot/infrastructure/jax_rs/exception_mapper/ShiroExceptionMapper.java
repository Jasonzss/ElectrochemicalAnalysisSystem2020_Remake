package com.bluedot.infrastructure.jax_rs.exception_mapper;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ShiroExceptionMapper implements ExceptionMapper<AuthorizationException> {

    @Override
    public Response toResponse(AuthorizationException exception) {

        Status status;

        if (exception instanceof UnauthorizedException) {
            status = Status.FORBIDDEN;
        } else {
            status = Status.UNAUTHORIZED;
        }

        return Response.status(status).build();
    }
}
