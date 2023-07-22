package com.bluedot.infrastructure.repository;

import com.bluedot.BaseTest;
import com.bluedot.domain.rbac.Role;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 9:52
 */
public class RoleRepositoryTest extends BaseTest {
    private RoleRepository repository;

    @Before
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        repository = context.getBean(RoleRepository.class);
    }

    @Test
    public void testSave(){
        Role role = new Role();
        role.setRoleName("测试实验员");
        role.setDescription("用于测试使用的实验员");
        Role save = repository.save(role);
        log.info(save.toString());
        repository.delete(save);
    }
}
