package com.bluedot.infrastructure.spring;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.springframework.beans.factory.FactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.stereotype.Component;

/**
 * @author Jason
 * @creationDate 2023/07/22 - 22:01
 */
@Component
public class SubjectFactoryBean implements FactoryBean<Subject> {
    private static final String path = "classpath:shiro_realm.ini";

    @Override
    public Subject getObject() {
        return getSubject();
    }

    @Override
    public Class<?> getObjectType() {
        return Subject.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }


    private static class EnumConverter extends AbstractConverter {
        @Override
        protected String convertToString(final Object value) {
            return ((Enum<?>) value).name();
        }

        @Override
        protected Object convertToType(final Class type, final Object value) {
            return Enum.valueOf(type, value.toString());
        }

        @Override
        protected Class<?> getDefaultType() {
            return null;
        }
    }

    public static Subject getSubject(){
        BeanUtilsBean.getInstance().getConvertUtils().register(new EnumConverter(), JdbcRealm.SaltStyle.class);
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(path);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        return SecurityUtils.getSubject();
    }
}
