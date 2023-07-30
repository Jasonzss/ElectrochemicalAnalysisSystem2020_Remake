package com.bluedot.resource;

import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.MaterialTypeRepository;
import com.bluedot.infrastructure.repository.data_object.MaterialType;
import com.bluedot.infrastructure.utils.ResourceUtil;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:19
 */
public class MaterialTypeResource {
    @Inject
    private MaterialTypeRepository repository;

    public MaterialType getMaterialType(int id){
        return repository.findById(id).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    public MaterialType addMaterialType(MaterialType materialType){
        return repository.save(materialType);
    }

    public MaterialType updateMaterialType(MaterialType materialType){
        return ResourceUtil.updateResource(materialType, materialType.getId(), repository);
    }

    public void deleteMaterialType(List<Integer> ids){
        repository.deleteAllById(ids);
    }

    public PageData<MaterialType> getMaterialTypePage(PageInfo pageInfo){
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }

    public MaterialTypeRepository getRepository() {
        return repository;
    }

    public void setRepository(MaterialTypeRepository repository) {
        this.repository = repository;
    }
}
