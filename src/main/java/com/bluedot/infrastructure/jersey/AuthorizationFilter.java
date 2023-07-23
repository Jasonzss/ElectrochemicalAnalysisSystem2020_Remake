package com.bluedot.infrastructure.jersey;

import com.bluedot.domain.rbac.exception.UserException;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.authz.aop.*;
import org.glassfish.jersey.server.internal.process.MappableException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A filter that grants or denies access to a JAX-RS resource based on the Shiro annotations on it.
 * 基于Shiro实现用于 JAX-RS 资源的【授权】或【权限过滤】的过滤器
 *
 * @see org.apache.shiro.authz.annotation
 */
public class AuthorizationFilter implements ContainerRequestFilter {

    private final Map<AuthorizingAnnotationHandler, Annotation> authzChecks;

    /**
     * 初始化验证内容，根据业务需求激活对应注解的验证逻辑
     * @param authzSpecs 需要被验证的注解
     */
    public AuthorizationFilter(Collection<Annotation> authzSpecs) {
        Map<AuthorizingAnnotationHandler, Annotation> authChecks = new HashMap<>(authzSpecs.size());
        for (Annotation authSpec : authzSpecs) {
            authChecks.put(createHandler(authSpec), authSpec);
        }
        this.authzChecks = Collections.unmodifiableMap(authChecks);
    }

    /**
     * 根据提供的注解生成对应的注解处理器，但目前只支持shiro注解的处理
     * @param annotation 需要被验证的注解
     * @return 注解的处理器
     */
    private static AuthorizingAnnotationHandler createHandler(Annotation annotation) {
        Class<?> t = annotation.annotationType();
        if (RequiresPermissions.class.equals(t)) return new PermissionAnnotationHandler();
        else if (RequiresRoles.class.equals(t)) return new RoleAnnotationHandler();
        else if (RequiresUser.class.equals(t)) return new UserAnnotationHandler();
        else if (RequiresGuest.class.equals(t)) return new GuestAnnotationHandler();
        else if (RequiresAuthentication.class.equals(t)) return new AuthenticatedAnnotationHandler();
        else throw new IllegalArgumentException("Cannot create a handler for the unknown for annotation " + t);
    }

    /**
     * 过滤逻辑：权限验证
     * @param requestContext 本次请求的上下文
     */
    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            for (Map.Entry<AuthorizingAnnotationHandler, Annotation> authzCheck : authzChecks.entrySet()) {
                AuthorizingAnnotationHandler handler = authzCheck.getKey();
                Annotation authzSpec = authzCheck.getValue();
                //判断当前线程对应的用户是否满足权限要求
                handler.assertAuthorized(authzSpec);
            }
        } catch (AuthorizationException e) {
            throw new UserException(CommonErrorCode.E_6007);
        }
    }
}
