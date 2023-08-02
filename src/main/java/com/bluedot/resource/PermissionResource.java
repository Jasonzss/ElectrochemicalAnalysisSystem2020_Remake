package com.bluedot.resource;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bluedot.domain.rbac.Permission;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.PermissionRepository;
import com.bluedot.infrastructure.utils.ResourceUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Optional;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:14
 */
@Path("permissions")
public class PermissionResource {
    @Inject
    private PermissionRepository repository;

    @Path("all")
    @RequiresPermissions("permission:get:*")
    public List<Permission> listPermission(){
        return repository.findAll();
    }

    @GET
    @RequiresPermissions("permission:get:*")
    public Permission getPermission(@QueryParam("permission-id") int permissionId){
        return repository.findById(permissionId).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @DELETE
    @RequiresPermissions("permission:delete:*")
    public void deletePermission(@QueryParam("id") int permissionId){
        repository.deleteById(permissionId);
    }

    @POST
    @RequiresPermissions("permission:create:*")
    public Permission addPermission(@BeanParam Permission permission){
        return repository.save(permission);
    }

    @PUT
    @RequiresPermissions("permission:update:*")
    public Permission updatePermission(@BeanParam Permission permission){
        return ResourceUtil.updateResource(permission, permission.getPermissionId(), repository);
    }

    public PermissionRepository getRepository() {
        return repository;
    }

    public void setRepository(PermissionRepository repository) {
        this.repository = repository;
    }
}
