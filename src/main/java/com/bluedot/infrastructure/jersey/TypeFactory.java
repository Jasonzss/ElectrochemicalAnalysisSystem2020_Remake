package com.bluedot.infrastructure.jersey;

import com.bluedot.infrastructure.shiro.Auth;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;

import javax.inject.Singleton;

/**
 * 修改自： https://github.com/silb/shiro-jersey
 *
 * Base class for factories that can instantiate object of a given type.
 * 能够实例化给定类型的基本抽象工厂
 *
 * Able to {@link ValueFactoryProvider provide} and {@link Binder bind} itself.
 * 由于实现了 ValueFactoryProvider, Binder 接口，所以具有自己提供工厂和自己决定依赖注入逻辑的能力
 *
 * @param <T> 此工厂类所提供对象的类型
 */
public abstract class TypeFactory<T> implements Factory<T>, ValueFactoryProvider, Binder {
    /**
     * 此工厂类所提供对象的类型
     */
    public final Class<T> type;

    public TypeFactory(Class<T> type) {
        this.type = type;
    }

    // org.glassfish.hk2.api.Factory<T> 的实现

    /**
     * 销毁对象
     * @param instance 被销毁的对象
     */
    @Override
    public void dispose(T instance) {}

    // org.glassfish.jersey.server.spi.internal.ValueFactoryProvider 的实现

    /**
     * Factory 的提供者，负责 Factory 的生成逻辑并返回
     * @param parameter Jersey方法的入参
     * @return 能够生成Jersey方法入参的工厂
     */
    @Override
    public Factory<?> getValueFactory(Parameter parameter) {
        if (type.equals(parameter.getRawType()) && parameter.isAnnotationPresent(Auth.class)) {
            return this;
        }
        return null;
    }

    /**
     * FactoryProvider 的优先级，有多个 FactoryProvider 能返回目标 Factory 时，
     * 获取优先度更高的 FactoryProvider 提供的 Factory
     * @return 当前 FactoryProvider 的优先级
     */
    @Override
    public PriorityType getPriority() {
        return Priority.NORMAL;
    }

    // org.glassfish.hk2.utilities.Binder 的实现

    /**
     * 依赖注入逻辑，让这个类作为 【T的Factory】和【ValueFactoryProvider】的依赖进行注入
     * @param config HK2的动态配置
     */
    @Override
    public void bind(DynamicConfiguration config) {
      Injections.addBinding(
              Injections.newFactoryBinder(this).to(type).in(Singleton.class),
              config);

      Injections.addBinding(
              Injections.newBinder(this).to(ValueFactoryProvider.class),
              config);
    }
}