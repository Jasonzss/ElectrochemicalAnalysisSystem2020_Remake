package com.bluedot.infrastructure.jersey;

import org.apache.shiro.authz.annotation.*;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Wraps {@link AuthorizationFilter filters} around JAX-RS resources that are annotated with Shiro annotations.
 * 注册shiro注解之于JAX-RS资源的管理功能
 *
 * shiro注解 权限验证 过滤器
 */
@Provider
public class AuthorizationFilterFeature implements DynamicFeature {

    /**
     * 启用的shiro注解
     */
    private static final List<Class<? extends Annotation>> shiroAnnotations = Collections.unmodifiableList(Arrays.asList(
            RequiresPermissions.class,
            RequiresRoles.class,
            RequiresAuthentication.class,
            RequiresUser.class,
            RequiresGuest.class));

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {

        List<Annotation> authzSpecs = new ArrayList<>();

        for (Class<? extends Annotation> annotationClass : shiroAnnotations) {
            // XXX What is the performance of getAnnotation vs getAnnotations?
            Annotation classAuthzSpec = resourceInfo.getResourceClass().getAnnotation(annotationClass);
            Annotation methodAuthzSpec = resourceInfo.getResourceMethod().getAnnotation(annotationClass);

            if (classAuthzSpec != null) authzSpecs.add(classAuthzSpec);
            if (methodAuthzSpec != null) authzSpecs.add(methodAuthzSpec);
        }

        if (!authzSpecs.isEmpty()) {
            //给目标资源注册shiro权限过滤器
            context.register(new AuthorizationFilter(authzSpecs), Priorities.AUTHORIZATION);
        }
    }

}
