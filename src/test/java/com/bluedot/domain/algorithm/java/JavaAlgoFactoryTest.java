package com.bluedot.domain.algorithm.java;

import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.domain.algorithm.exception.AlgorithmException;
import com.bluedot.domain.algorithm.PersistantAlgorithm;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.Optional;

/**
 * @Author Jason
 * @CreationDate 2023/06/02 - 1:01
 * @Description ：
 */
public class JavaAlgoFactoryTest {
    JavaAlgoFactory factory;

    @Before
    public void init(){
        factory = new JavaAlgoFactory();

        PersistantAlgorithm algorithm = new PersistantAlgorithm("java_test");
        PersistantAlgorithm algorithm1 = new PersistantAlgorithm("java_no_file");
        PersistantAlgorithm algorithm2 = new PersistantAlgorithm("java_test_compile");
        //使用mockito模拟一个AlgorithmRepository出来
        AlgorithmRepository repository = Mockito.mock(AlgorithmRepository.class);
        Mockito.when(repository.findById("java_test")).thenReturn(Optional.of(algorithm));
        Mockito.when(repository.findById("java_no_file")).thenReturn(Optional.of(algorithm1));
        Mockito.when(repository.findById("java_test_compile")).thenReturn(Optional.of(algorithm2));
        factory.setAlgorithmRepository(repository);
    }

    @After
    public void delete(){
        //删除测试过程中产生的文件
        File file = new File("src\\main\\resources\\algo\\java\\java_test_compile\\JavaAlgorithm.class");
        file.deleteOnExit();
    }

    /**
     * 正常获取存在的算法
     */
    @Test
    public void testGetAlgorithm(){
        Algorithm java = factory.getAlgorithm("java_test");
        Assert.assertNotNull(java);
    }

    /**
     * 获取数据库中未收录的算法
     */
    @Test(expected = AlgorithmException.class)
    public void testGetAlgorithm_AlgoException(){
        Algorithm java = factory.getAlgorithm("java_not_exist");
    }

    /**
     * 获取数据库中收录了但是文件系统中不存在的算法
     */
    @Test(expected = AlgorithmException.class)
    public void testGetAlgorithm_NoFile(){
        Algorithm java = factory.getAlgorithm("java_no_file");
    }

    /**
     * 获取数据库中收录了但是文件系统中只有java文件没有class文件
     */
    @Test
    public void testGetAlgorithm_NoClass(){
        Algorithm java = factory.getAlgorithm("java_test_compile");
        Assert.assertNotNull(java);
    }
}
