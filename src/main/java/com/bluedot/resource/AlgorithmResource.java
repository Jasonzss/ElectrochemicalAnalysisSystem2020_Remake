package com.bluedot.resource;

import com.bluedot.domain.algorithm.PersistantAlgorithm;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;

import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:14
 */
public class AlgorithmResource {
    public PersistantAlgorithm getPersistantAlgorithm(int id){
        return null;
    }

    public PersistantAlgorithm addPersistantAlgorithm(PersistantAlgorithm algorithm){
        return null;
    }

    public PersistantAlgorithm updatePersistantAlgorithm(PersistantAlgorithm algorithm){
        return null;
    }

    public void deletePersistantAlgorithm(List<Integer> ids){

    }
    public PageData<PersistantAlgorithm> getPersistantAlgorithmPage(PageInfo pageInfo){
        return null;
    }

    public PageData<PersistantAlgorithm> getPersistantAlgorithmPageByEmail(String email, PageInfo pageInfo){
        return null;
    }
}
