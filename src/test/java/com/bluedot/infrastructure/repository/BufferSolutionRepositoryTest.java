package com.bluedot.infrastructure.repository;

import com.bluedot.BaseTest;
import com.bluedot.infrastructure.repository.data_object.BufferSolution;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Jason
 * @creationDate 2023/07/13 - 21:27
 */
public class BufferSolutionRepositoryTest extends BaseTest {
    BufferSolutionRepository repository;
    Integer id;

    @Before
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        repository = context.getBean(BufferSolutionRepository.class);

        BufferSolution bufferSolution = new BufferSolution();
        bufferSolution.setName("测试溶液");
        bufferSolution.setDescription("测试描述");
        BufferSolution save = repository.save(bufferSolution);
        id = save.getId();
    }

    @After
    public void after(){
        repository.flush();
    }

    @Test
    public void testFindById(){
        printOptional(repository.findById(id));
        repository.deleteById(id);
    }

    @Test
    public void testDeleteById(){
        repository.deleteById(id);
    }
}
