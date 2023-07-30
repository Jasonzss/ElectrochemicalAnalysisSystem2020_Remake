package com.bluedot.resource;

import com.bluedot.domain.algorithm.PersistantAlgorithm;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import com.bluedot.infrastructure.utils.ResourceUtil;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;
import org.springframework.data.domain.Example;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:14
 */
public class AlgorithmResource {
    @Inject
    private AlgorithmRepository repository;

    public PersistantAlgorithm getPersistantAlgorithm(String id){
        return repository.findById(id).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    public PersistantAlgorithm addPersistantAlgorithm(PersistantAlgorithm algorithm){
        return repository.save(algorithm);
    }

    public PersistantAlgorithm updatePersistantAlgorithm(PersistantAlgorithm algorithm){
        return ResourceUtil.updateResource(algorithm, algorithm.getAlgoId(), repository);
    }

    public void deletePersistantAlgorithm(List<String> ids){
        repository.deleteAllById(ids);
    }
    public PageData<PersistantAlgorithm> getPersistantAlgorithmPage(PageInfo pageInfo){
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }

    public PageData<PersistantAlgorithm> getPersistantAlgorithmPageByEmail(String email, PageInfo pageInfo){
        User user = new User(email);
        PersistantAlgorithm algorithm = new PersistantAlgorithm();
        algorithm.setCreator(user);
        Example<PersistantAlgorithm> algorithmExample = Example.of(algorithm);

        return PageData.of(repository.findAll(algorithmExample, pageInfo.getPageable()));
    }

    public AlgorithmRepository getRepository() {
        return repository;
    }

    public void setRepository(AlgorithmRepository repository) {
        this.repository = repository;
    }
}
