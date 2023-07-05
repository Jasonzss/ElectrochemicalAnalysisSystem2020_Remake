package com.bluedot.infrastructure.repository;

import com.bluedot.domain.algorithm.PersistantAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;

/**
 * @author Jason
 * @creationDate 2023/07/05 - 16:29
 */
public class AlgorithmRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(SpringApp.class);

    AlgorithmRepository repository;

    @Before
    public void init(){
        @SuppressWarnings("resource")
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        repository = context.getBean(AlgorithmRepository.class);
    }

    @Test
    public void testFindById(){
        Optional<PersistantAlgorithm> byId = repository.findById("123456");
        byId.ifPresent(a -> {
            log.info(a.toString());
        });
    }

    @Test
    public void testListAvailable(){
        List<PersistantAlgorithm> a = repository.listAvailable("456");
        log.info("查询到uid：123456的用户可用的算法共"+a.size()+"个");
    }
}
