package com.bluedot.domain.algorithm;

import com.bluedot.domain.algorithm.exception.AlgorithmException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jason
 * @creationDate 2023/06/26 - 18:25
 */
public class SimpleAlgoFactory implements AlgoFactory{
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

    public void addFactory(AlgoFactory factory){
        factoryList.add(factory);
    }
}
