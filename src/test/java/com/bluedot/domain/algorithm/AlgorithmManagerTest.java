package com.bluedot.domain.algorithm;

import cn.hutool.core.io.FileUtil;
import com.bluedot.BaseTest;
import com.bluedot.domain.algorithm.exception.AlgorithmException;
import com.bluedot.domain.algorithm.java.JavaAlgoFactory;
import com.bluedot.domain.algorithm.po.PersistantAlgorithm;
import com.bluedot.domain.algorithm.python.PythonAlgoFactory;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * @author Jason
 * @creationDate 2023/06/26 - 17:38
 */
public class AlgorithmManagerTest extends BaseTest {
    private AlgorithmManager manager;
    private PersistantAlgorithm algo;
    private PersistantAlgorithm algoUpdated;
    AlgorithmRepository repository = Mockito.mock(AlgorithmRepository.class);

    @Before
    public void init(){
        //Mockito构建AlgorithmRepository
        PersistantAlgorithm algorithm = new PersistantAlgorithm("java_test");
        PersistantAlgorithm algorithm1 = new PersistantAlgorithm("java_not_exist_file");
        PersistantAlgorithm algorithm2 = new PersistantAlgorithm("java_test_compile");
        Mockito.when(repository.findById("java_test")).thenReturn(Optional.of(algorithm));
        Mockito.when(repository.findById("java_not_exist_file")).thenReturn(Optional.of(algorithm1));
        Mockito.when(repository.findById("java_test_compile")).thenReturn(Optional.of(algorithm2));

        algo = new PersistantAlgorithm("java_test_create");
        algo.setAlgoType("java");
        algo.setAlgoName("算法名称");
        algo.setInfo("一些信息");
        algo.setStatus(AlgorithmStatus.PRIVATE);
        Mockito.when(repository.save(algo)).thenReturn(algo);
        Mockito.when(repository.findById("java_test_create")).thenReturn(Optional.of(algo));

        algoUpdated = new PersistantAlgorithm("java_test_create");
        algoUpdated.setAlgoName("修改名称");
        algoUpdated.setInfo("修改后的一些信息");

        Mockito.doAnswer(invocationOnMock -> {
            Mockito.when(repository.findById("java_test_create")).thenReturn(Optional.empty());
            return null;
        }).when(repository).deleteById("java_test_create");

        Mockito.doAnswer(invocationOnMock -> {
            Mockito.when(repository.findById("java_test_create")).thenReturn(Optional.empty());
            return null;
        }).when(repository).save(algoUpdated);

        //构建AlgoFactory
        JavaAlgoFactory javaAlgoFactory = new JavaAlgoFactory();
        javaAlgoFactory.setAlgorithmRepository(repository);

        PythonAlgoFactory pythonAlgoFactory = new PythonAlgoFactory();
        pythonAlgoFactory.setAlgorithmRepository(repository);

        SimpleAlgoFactory algoFactory = new SimpleAlgoFactory();
        algoFactory.addFactory(javaAlgoFactory);
        algoFactory.addFactory(pythonAlgoFactory);

        //构建AlgorithmManager
        manager = new AlgorithmManager();
        manager.setAlgoFactory(algoFactory);
        manager.setRepository(repository);
    }

    @After
    public void del(){
        FileUtil.del(new File("src\\main\\resources\\algo\\java\\java_test_create"));
    }

    /* -------------------------Java算法相关测试------------------------ */

    @Test
    public void testGetAlgorithm(){
        Algorithm java = manager.getAlgorithm("java_test");
        java.execute(25);
    }

    @Test
    public void testCreateAlgorithm() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src\\test\\resources\\test_file\\JavaAlgorithm.java");

        Algorithm algorithm1 = manager.createAlgorithm(fileInputStream, algo);
        algorithm1.execute(32);
    }

    @Test(expected = AlgorithmException.class)
    public void testDeleteAlgorithm() throws FileNotFoundException {
        testCreateAlgorithm();
        manager.deleteAlgorithm("java_test_create");
        Algorithm create = manager.getAlgorithm("java_test_create");
        create.execute(66);
    }

    @Test
    public void testUpdateAlgorithm(){
        manager.updateAlgorithm(algoUpdated);
        PersistantAlgorithm create = repository.findById("java_test_create").get();
        log.debug(create.toString());
    }

    @Test
    public void testUpdateAlgorithmWithInputStream() throws FileNotFoundException {
        testCreateAlgorithm();
        FileInputStream fis = new FileInputStream("src\\test\\resources\\test_file\\UpdatedAlgorithm.java");
        long updated = manager.updateAlgorithm("java_test_create", fis);
        log.info("更新的字节数："+updated);
        Algorithm create = manager.getAlgorithm("java_test_create");
        create.execute("jason");
    }

    /* -------------------------Python算法相关测试------------------------ */
}
