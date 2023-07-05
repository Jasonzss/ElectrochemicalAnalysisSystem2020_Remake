package com.bluedot.domain.algorithm;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.bluedot.BaseTest;
import com.bluedot.domain.algorithm.exception.AlgorithmException;
import com.bluedot.domain.algorithm.java.JavaAlgoFactory;
import com.bluedot.infrastructure.repository.enumeration.AlgorithmStatus;
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
    private PersistantAlgorithm pAlgo;
    private PersistantAlgorithm algoUpdated;
    AlgorithmRepository repository = Mockito.mock(AlgorithmRepository.class);

    @Before
    public void init(){
        //Mockito构建AlgorithmRepository
        PersistantAlgorithm algorithm = new PersistantAlgorithm("java_test");
        PersistantAlgorithm algorithm1 = new PersistantAlgorithm("java_not_exist_file");
        PersistantAlgorithm algorithm2 = new PersistantAlgorithm("java_test_compile");
        PersistantAlgorithm algorithm5 = new PersistantAlgorithm("python_test_error");
        PersistantAlgorithm algorithm6 = new PersistantAlgorithm("python_test_hello");
        PersistantAlgorithm algorithm7 = new PersistantAlgorithm("python_test_params");
        Mockito.when(repository.findById("java_test")).thenReturn(Optional.of(algorithm));
        Mockito.when(repository.findById("java_not_exist_file")).thenReturn(Optional.of(algorithm1));
        Mockito.when(repository.findById("java_test_compile")).thenReturn(Optional.of(algorithm2));
        Mockito.when(repository.findById("python_test_error")).thenReturn(Optional.of(algorithm5));
        Mockito.when(repository.findById("python_test_hello")).thenReturn(Optional.of(algorithm6));
        Mockito.when(repository.findById("python_test_params")).thenReturn(Optional.of(algorithm7));

        //Mockito模拟Java算法相关逻辑
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

        //Mockito模拟Python算法相关逻辑
        pAlgo = new PersistantAlgorithm("python_test_create");
        pAlgo.setStatus(AlgorithmStatus.PRIVATE);
        pAlgo.setAlgoName("python算法名称");
        pAlgo.setInfo("python算法相关信息");
        pAlgo.setAlgoType("python");

        Mockito.doAnswer(invocation -> {
            Mockito.when(repository.findById("python_test_create")).thenReturn(Optional.of(pAlgo));
            return pAlgo;
        }).when(repository).save(pAlgo);

        Mockito.doAnswer(invocation -> {
            Mockito.when(repository.findById("python_test_create")).thenReturn(Optional.empty());
            return null;
        }).when(repository).deleteById("python_test_create");

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
        FileUtil.del(new File("src\\main\\resources\\algo\\python\\python_test_create"));
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

    @Test
    public void testGetAlgorithm_Python(){
        Algorithm python = manager.getAlgorithm("python_test_hello");
        log.debug((String) python.execute());
    }

    @Test
    public void testGetAlgorithm_PythonParams(){
        Algorithm python = manager.getAlgorithm("python_test_params");
        log.debug((String) python.execute("jason", DateUtil.date().toString()));
    }

    @Test
    public void testGetAlgorithm_MultiPython(){
        Algorithm python = manager.getAlgorithm("python_test_hello");
        log.debug((String) python.execute());
        Algorithm python1 = manager.getAlgorithm("python_test_hello");
        log.debug((String) python1.execute());
        Algorithm python3 = manager.getAlgorithm("python_test_params");
        log.debug((String) python3.execute("jason", DateUtil.date().toString()));
    }

    @Test
    public void testCreateAlgorithm_Python() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src\\test\\resources\\test_file\\python_algorithm.py");
        Algorithm algorithm1 = manager.createAlgorithm(fileInputStream, pAlgo);
        algorithm1.execute("你好");
    }

    @Test(expected = AlgorithmException.class)
    public void testDeleteAlgorithm_Python() throws FileNotFoundException {
        testCreateAlgorithm_Python();
        manager.deleteAlgorithm("python_test_create");
        Algorithm create = manager.getAlgorithm("python_test_create");
        create.execute("删除了应该就掉用不了了");
    }

    @Test
    public void testUpdateAlgorithmWithInputStream_() throws FileNotFoundException {
        testCreateAlgorithm_Python();
        FileInputStream fis = new FileInputStream("src\\test\\resources\\test_file\\updated_algorithm.py");
        long updated = manager.updateAlgorithm("python_test_create", fis);
        log.info("更新的字节数："+updated);
        Algorithm create = manager.getAlgorithm("python_test_create");
        create.execute("jason");
    }
}
