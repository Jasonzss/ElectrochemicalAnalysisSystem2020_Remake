package com.bluedot.infrastructure.jersey;

import com.bluedot.infrastructure.shiro.SubjectFactoryBean;
import org.apache.shiro.subject.Subject;
import org.glassfish.hk2.api.PerLookup;

import javax.ws.rs.ext.Provider;

/**
 * Creates {@link Subject subjects} to be used as injected values.
 *
 * 类似于Spring中的FactoryBean，负责创建Subject对象并注入到需要它的对象中
 */
@Provider
public class SubjectFactory extends TypeFactory<Subject> {

    public SubjectFactory() {
        super(Subject.class);
    }

    @PerLookup
    @Override
    public Subject provide() {
        return SubjectFactoryBean.getSubject();
    }
}
