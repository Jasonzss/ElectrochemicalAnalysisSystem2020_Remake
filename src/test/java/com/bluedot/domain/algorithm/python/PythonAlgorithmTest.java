package com.bluedot.domain.algorithm.python;

import cn.hutool.core.date.DateUtil;
import com.bluedot.BaseTest;
import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.domain.algorithm.exception.AlgoInvokeException;
import com.bluedot.domain.algorithm.po.PersistantAlgorithm;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

/**
 * @author Jason
 * @creationDate 2023/07/01 - 14:25
 */
public class PythonAlgorithmTest extends BaseTest {
    PythonAlgoFactory factory;

    @Before
    public void init(){
        factory = new PythonAlgoFactory();

        PersistantAlgorithm algorithm = new PersistantAlgorithm("python_test_hello");
        PersistantAlgorithm algorithm1 = new PersistantAlgorithm("python_file_not_found");
        PersistantAlgorithm algorithm2 = new PersistantAlgorithm("python_test_params");
        PersistantAlgorithm algorithm3 = new PersistantAlgorithm("python_test_error");

        AlgorithmRepository repository = Mockito.mock(AlgorithmRepository.class);
        Mockito.when(repository.findById("python_test_hello")).thenReturn(Optional.of(algorithm));
        Mockito.when(repository.findById("python_file_not_found")).thenReturn(Optional.of(algorithm1));
        Mockito.when(repository.findById("python_test_params")).thenReturn(Optional.of(algorithm2));
        Mockito.when(repository.findById("python_test_error")).thenReturn(Optional.of(algorithm3));

        factory.setAlgorithmRepository(repository);
    }

    @Test
    public void testHello() {
        Algorithm pythonAlgorithm = factory.getAlgorithm("python_test_hello");
        try{
            System.out.println(pythonAlgorithm.execute());
        }catch (AlgoInvokeException e){
            if(e.getErrorCode() == CommonErrorCode.E_3012){
                log.warn("测试此方法请开启PythonWeb端");
            }else{
                throw e;
            }
        }
    }

    @Test
    public void testParams() {
        Algorithm pythonAlgorithm = factory.getAlgorithm("python_test_params");
        try{
            System.out.println(pythonAlgorithm.execute("jason", DateUtil.date().toString()));
        }catch (AlgoInvokeException e){
            if(e.getErrorCode() == CommonErrorCode.E_3012){
                log.warn("测试此方法请开启PythonWeb端");
            }else{
                throw e;
            }
        }
    }

    @Test(expected = PythonInvokeException.class)
    public void testWrongParams() {
        Algorithm pythonAlgorithm = factory.getAlgorithm("python_test_params");
        try{
            System.out.println(pythonAlgorithm.execute("jason"));
        }catch (AlgoInvokeException e){
            if(e.getErrorCode() == CommonErrorCode.E_3012){
                log.warn("测试此方法请开启PythonWeb端");
            }else{
                throw e;
            }
        }
    }

    @Test(expected = PythonInvokeException.class)
    public void testErrorPython() {
        Algorithm pythonAlgorithm = factory.getAlgorithm("python_test_error");
        try{
            System.out.println(pythonAlgorithm.execute());
        }catch (AlgoInvokeException e){
            if(e.getErrorCode() == CommonErrorCode.E_3012){
                log.warn("测试此方法请开启PythonWeb端");
            }else{
                throw e;
            }
        }
    }
}
