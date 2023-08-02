package com.bluedot.infrastructure.repository;

import com.bluedot.BaseTest;
import com.bluedot.domain.rbac.RolePermission;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * @author Jason
 * @since 2023/08/01 - 14:06
 */
public class RolePermissionRepositoryTest extends BaseTest {
    private RolePermissionRepository repository;

    @Before
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        repository = context.getBean(RolePermissionRepository.class);
    }

    @Test
    public void test(){
        List<RolePermission> permissionsByRoleId = repository.findPermissionsByRoleId(6);
        log.info(permissionsByRoleId.toString());
    }
}
