package com.bluedot.resource;

import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.MaterialTypeRepository;
import com.bluedot.infrastructure.repository.data_object.MaterialType;
import com.bluedot.infrastructure.utils.ResourceUtil;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:19
 */
@Path("material-type")
public class MaterialTypeResource {
    @Inject
    private MaterialTypeRepository repository;

    @GET
    @Path("{id}")
    @RequiresPermissions("material-type:get")
    public MaterialType getMaterialType(@PathParam("id") int id){
        return repository.findById(id).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @POST
    @RequiresPermissions("material-type:create")
    public MaterialType addMaterialType(@BeanParam MaterialType materialType){
        return repository.save(materialType);
    }

    @PUT
    @RequiresPermissions("material-type:update")
    public MaterialType updateMaterialType(@BeanParam MaterialType materialType){
        return ResourceUtil.updateResource(materialType, materialType.getId(), repository);
    }

    @DELETE
    @RequiresPermissions("material-type:delete")
    public void deleteMaterialType(@QueryParam("id") List<Integer> ids){
        repository.deleteAllById(ids);
    }

    @Path("pages")
    @RequiresPermissions("material-type:get")
    public PageData<MaterialType> getMaterialTypePage(@BeanParam PageInfo pageInfo){
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }

    public MaterialTypeRepository getRepository() {
        return repository;
    }

    public void setRepository(MaterialTypeRepository repository) {
        this.repository = repository;
    }
}
