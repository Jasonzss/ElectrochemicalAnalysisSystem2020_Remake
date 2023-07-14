package com.bluedot.infrastructure.repository;

import com.bluedot.BaseTest;
import com.bluedot.infrastructure.repository.data_object.MaterialType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Jason
 * @creationDate 2023/07/13 - 21:28
 */
public class MaterialTypeRepositoryTest extends BaseTest {
    MaterialTypeRepository repository;

    @Before
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        repository = context.getBean(MaterialTypeRepository.class);
    }

    @After
    public void after(){
        repository.flush();
    }

    @Test
    public void testFindById(){
        MaterialType type = new MaterialType();
        type.setName("测试名称");
        type.setDescription("测试描述");

        MaterialType save = repository.save(type);
        log.info(save.toString());
        repository.deleteById(save.getId());
    }
}
