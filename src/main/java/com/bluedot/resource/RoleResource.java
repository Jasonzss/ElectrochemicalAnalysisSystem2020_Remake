package com.bluedot.resource;

import com.bluedot.domain.rbac.Permission;
import com.bluedot.domain.rbac.Role;
import com.bluedot.domain.rbac.RolePermission;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.RolePermissionRepository;
import com.bluedot.infrastructure.repository.RoleRepository;
import com.bluedot.infrastructure.utils.ResourceUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:13
 */
@Path("roles")
public class RoleResource {
    @Inject
    private RoleRepository repository;

    @Inject
    private RolePermissionRepository rolePermissionRepository;

    public List<Role> listRole(){
        return repository.findAll();
    }

    @GET
    @Path("{id}")
    @RequiresPermissions("role:get:*")
    public Role getRole(@PathParam("id") int roleId){
        return repository.findById(roleId).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @DELETE
    @RequiresPermissions("role:delete:*")
    public void deleteRole(@QueryParam("id") int roleId){
        repository.deleteById(roleId);
    }

    @POST
    @RequiresPermissions("role:create:*")
    public Role addRole(@BeanParam Role role){
        return repository.save(role);
    }

    @PUT
    @RequiresPermissions("role:update:*")
    public Role updateRole(@BeanParam Role role){
        return ResourceUtil.updateResource(role, role.getRoleId(), repository);
    }

    @GET
    @Path("{id}/permissions")
    @RequiresPermissions("role:get:*")
    public List<Permission> getPermissionByRole(@PathParam("id") int roleId) {
        List<RolePermission> permissionsByRoleId = rolePermissionRepository.findPermissionsByRoleId(roleId);
        return null;
    }

    public RoleRepository getRepository() {
        return repository;
    }

    public void setRepository(RoleRepository repository) {
        this.repository = repository;
    }

    public RolePermissionRepository getRolePermissionRepository() {
        return rolePermissionRepository;
    }

    public void setRolePermissionRepository(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }
}
