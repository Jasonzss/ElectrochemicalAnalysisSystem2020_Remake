package com.bluedot.infrastructure.repository;

import com.bluedot.BaseTest;
import com.bluedot.domain.rbac.Permission;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:07
 */
public class PermissionRepositoryTest extends BaseTest {
    private PermissionRepository repository;

    @Before
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        repository = context.getBean(PermissionRepository.class);
    }

    @Test
    public void testSave(){
        Permission permission = new Permission();
        permission.setPermissionName("测试权限");
        permission.setDescription("用于测试的权限");
        Permission save = repository.save(permission);
        log.info(save.toString());
        repository.delete(save);
    }
}
