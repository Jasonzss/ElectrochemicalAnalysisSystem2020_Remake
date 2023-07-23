package com.bluedot.infrastructure.jersey;

import com.bluedot.infrastructure.shiro.Auth;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;

/**
 * For method parameter injection with the {@linkplain Auth} annotation.
 * 方法参数注入解析器，当某个Jersey方法的入参中使用Auth注解注释时会尝试注入Auth标记的对象
 */
public class AuthParamInjectionResolver extends ParamInjectionResolver<Auth> {

    public AuthParamInjectionResolver() {
        //提供自定义的TypeFactory解析Auth并返回期望的对象
        super(TypeFactory.class);
    }
}
