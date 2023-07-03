package com.bluedot.domain.algorithm.java;

import com.bluedot.BaseTest;
import com.bluedot.domain.algorithm.exception.AlgoInvokeException;
import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.domain.algorithm.po.PersistantAlgorithm;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.Optional;

/**
 * @author Jason
 * @creationDate 2023/06/02 - 14:10
 */
public class JavaAlgorithmTest extends BaseTest {
    JavaAlgoFactory factory;

    @Before
    public void init(){
        factory = new JavaAlgoFactory();


        PersistantAlgorithm algorithm = new PersistantAlgorithm("java_test");
        PersistantAlgorithm algorithm2 = new PersistantAlgorithm("java_test_compile");
        PersistantAlgorithm algorithm3 = new PersistantAlgorithm("java_test_exception");
        PersistantAlgorithm algorithm4 = new PersistantAlgorithm("java_test_syntax_error");
        //使用mockito模拟一个AlgorithmRepository出来
        AlgorithmRepository repository = Mockito.mock(AlgorithmRepository.class);
        Mockito.when(repository.findById("java_test")).thenReturn(Optional.of(algorithm));
        Mockito.when(repository.findById("java_test_compile")).thenReturn(Optional.of(algorithm2));
        Mockito.when(repository.findById("java_test_exception")).thenReturn(Optional.of(algorithm3));
        Mockito.when(repository.findById("java_test_syntax_error")).thenReturn(Optional.of(algorithm4));
        factory.setAlgorithmRepository(repository);
    }

    @After
    public void delete(){
        //删除测试过程中产生的文件
        File file = new File("src\\main\\resources\\algo\\java\\java_test_compile\\JavaAlgorithm.class");
        file.deleteOnExit();
    }
    /**
     * 执行正常的算法
     */
    @Test
    public void testGetAlgorithm(){
        JavaAlgorithm java = (JavaAlgorithm) factory.getAlgorithm("java_test");
        int a = 12;
        java.execute(a);
    }

    /**
     * 执行多个正常的算法
     */
    @Test
    public void testGet2Algorithm(){
        Algorithm java = factory.getAlgorithm("java_test");
        int a = 12;
        java.execute(a);

        Algorithm java1 = factory.getAlgorithm("java_test_compile");
        java1.execute();
    }


    /**
     * 执行没有class文件只有java文件的算法
     */
    @Test
    public void testGetAlgorithm_NoClass() throws InterruptedException {
        Algorithm java = factory.getAlgorithm("java_test_compile");
        java.execute();
    }

    /**
     * 执行抛出异常的算法
     */
    @Test(expected = AlgoInvokeException.class)
    public void testGetAlgorithm_exception(){
        Algorithm java = factory.getAlgorithm("java_test_exception");
        java.execute();
    }

    /**
     * TODO 执行存在语法错误的java文件的算法
     */
//    @Test
    public void testGetAlgorithm_syntaxError(){
        Algorithm java = factory.getAlgorithm("java_test_syntax_error");
        java.execute();
    }
}
