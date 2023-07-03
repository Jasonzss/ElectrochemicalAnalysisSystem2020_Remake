package com.bluedot.domain.algorithm.python;

import com.bluedot.BaseTest;
import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.domain.algorithm.exception.AlgorithmException;
import com.bluedot.domain.algorithm.po.PersistantAlgorithm;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

/**
 * @author Jason
 * @creationDate 2023/06/22 - 17:46
 */
public class PythonAlgoFactoryTest extends BaseTest {
    PythonAlgoFactory factory;

    @Before
    public void init(){
        factory = new PythonAlgoFactory();

        PersistantAlgorithm algorithm = new PersistantAlgorithm("python_test_hello");
        PersistantAlgorithm algorithm1 = new PersistantAlgorithm("python_file_not_found");

        AlgorithmRepository repository = Mockito.mock(AlgorithmRepository.class);
        Mockito.when(repository.findById("python_test_hello")).thenReturn(Optional.of(algorithm));
        Mockito.when(repository.findById("python_file_not_found")).thenReturn(Optional.of(algorithm1));

        factory.setAlgorithmRepository(repository);
    }

    @Test
    public void testHello() {
        Algorithm pythonAlgorithm = factory.getAlgorithm("python_test_hello");
        Assert.assertNotNull(pythonAlgorithm);
    }

    @Test(expected = AlgorithmException.class)
    public void testNotFoundInDB() {
        Algorithm pythonAlgorithm = factory.getAlgorithm("python_not_found_in_DB");
        Assert.assertNotNull(pythonAlgorithm);
    }

    @Test(expected = AlgorithmException.class)
    public void testFileNotFound() {
        Algorithm pythonAlgorithm = factory.getAlgorithm("python_file_not_found");
        Assert.assertNotNull(pythonAlgorithm);
    }
}
