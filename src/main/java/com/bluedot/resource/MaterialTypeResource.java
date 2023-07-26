package com.bluedot.resource;

import com.bluedot.infrastructure.repository.data_object.MaterialType;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;

import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:19
 */
public class MaterialTypeResource {
    public MaterialType getMaterialType(int id){
        return null;
    }

    public MaterialType addMaterialType(MaterialType materialType){
        return null;
    }

    public MaterialType updateMaterialType(MaterialType materialType){
        return null;
    }

    public void deleteMaterialType(List<Integer> ids){

    }

    public PageData<MaterialType> getMaterialTypePage(PageInfo pageInfo){
        return null;
    }

    public PageData<MaterialType> getMaterialTypePageByEmail(String email, PageInfo pageInfo){
        return null;
    }
}
