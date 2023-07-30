package com.bluedot.resource;

import com.bluedot.domain.rbac.Role;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.RoleRepository;

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

    public List<Role> listRole(){
        return repository.findAll();
    }

    @GET
    @Path("{id}")
    public Role getRole(@PathParam("id") int roleId){
        return repository.findById(roleId).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    public void deleteRole(int roleId){
        repository.deleteById(roleId);
    }

    public Role addRole(String roleName, String roleDesc){
        Role role = new Role();
        role.setRoleName(roleName);
        role.setDescription(roleDesc);
        return repository.save(role);
    }

    public Role updateRole(int roleId, String roleName, String roleDesc){
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setDescription(roleDesc);
        return repository.save(role);
    }

    public RoleRepository getRepository() {
        return repository;
    }

    public void setRepository(RoleRepository repository) {
        this.repository = repository;
    }
}
