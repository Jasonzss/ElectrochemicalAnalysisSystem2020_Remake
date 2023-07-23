package com.bluedot.infrastructure.jersey;

import com.bluedot.infrastructure.shiro.Auth;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * Enable injection with the {@link Auth} annotation.
 * <p>
 * 启用 {@link Auth} 注解的方法入参注入
 */
public class AuthInjectionBinder extends AbstractBinder {

    /**
     * 让 AuthParamInjectionResolver 作为 {@link Auth} 注解的解析类
     */
    @Override
    protected void configure() {
        bind(AuthParamInjectionResolver.class).in(Singleton.class)
            .to(new TypeLiteral<InjectionResolver<Auth>>() {});
    }
}
