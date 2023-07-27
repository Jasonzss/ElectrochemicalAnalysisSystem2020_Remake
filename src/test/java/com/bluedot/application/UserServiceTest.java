package com.bluedot.application;

import com.bluedot.infrastructure.repository.AlgorithmRepository;
import com.bluedot.infrastructure.repository.SpringApp;
import com.bluedot.infrastructure.repository.UserRepository;
import com.bluedot.infrastructure.shiro.SubjectFactoryBean;
import com.bluedot.resource.BaseJerseyTest;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Jason
 * @since 2023/07/27 - 18:14
 */
public class UserServiceTest extends BaseJerseyTest {
    private UserService userService;

    @Before
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        userService = context.getBean(UserService.class);
    }

    @Test
    public void testLogin(){
        userService.login(EMAIL, PASSWORD, true);
    }
}
