package com.bluedot.infrastructure.repository;

import com.bluedot.BaseTest;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.repository.enumeration.UserStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

/**
 * @author Jason
 * @creationDate 2023/07/13 - 21:28
 */
public class UserRepositoryTest extends BaseTest {
    UserRepository repository;

    @Before
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        repository = context.getBean(UserRepository.class);
    }

    @Test
    public void testFindById(){
        Optional<User> byId = repository.findById("2418972236@qq.com");
        byId.ifPresent(System.out::println);
    }

    @Test
    public void testSave(){
        User u = new User();
        u.setEmail("12345@Test.com");
        u.setUsername("测试名称");
        u.setPassword("加密密码");
        u.setSalt("加密盐");
        u.setUserImg("暂无头像");
        u.setSex("男");
        u.setUserStatus(UserStatus.NORM);
        User save = repository.save(u);
        log.info(save.toString());
        repository.deleteById(u.getEmail());
    }

    @Test
    public void testFindSalt(){
        User saltByEmail = repository.findSaltByEmail("2418972236@qq.com");
        log.info(saltByEmail.getSalt());
    }
}
