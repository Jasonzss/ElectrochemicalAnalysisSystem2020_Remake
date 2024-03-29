package com.bluedot.infrastructure.repository;

import com.bluedot.BaseTest;
import com.bluedot.domain.rbac.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 9:52
 */
public class UserRoleRepositoryTest extends BaseTest {
    UserRoleRepository repository;

    @Before
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        repository = context.getBean(UserRoleRepository.class);
    }

    @Test
    public void test(){
        UserRole userRole = new UserRole();
        userRole.setRoleId(7);
        userRole.setEmail("2418972236@qq.com");
        UserRole save = repository.save(userRole);
        log.info(save.toString());
    }
}
