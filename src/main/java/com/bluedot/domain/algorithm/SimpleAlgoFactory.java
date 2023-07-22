package com.bluedot.domain.algorithm;

import com.bluedot.domain.algorithm.exception.AlgorithmException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/06/26 - 18:25
 */
@Component
public class SimpleAlgoFactory implements AlgoFactory, BeanPostProcessor {
    private final List<AlgoFactory> factoryList = new ArrayList<>();

    @Override
    public Algorithm getAlgorithm(String algoId) throws AlgorithmException {
        for(AlgoFactory factory : factoryList){
            Algorithm algorithm = factory.getAlgorithm(algoId);
            if(algorithm != null){
                return algorithm;
            }
        }

        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(AlgoFactory.class.isAssignableFrom(bean.getClass())){
            factoryList.add((AlgoFactory) bean);
        }

        return bean;
    }

    public void addFactory(AlgoFactory factory){
        factoryList.add(factory);
    }
}
