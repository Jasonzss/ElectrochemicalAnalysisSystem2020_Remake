package com.bluedot.resource;

import com.bluedot.domain.rbac.Role;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.RoleRepository;
import com.bluedot.infrastructure.utils.ResourceUtil;

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

    @DELETE
    public void deleteRole(@QueryParam("id") int roleId){
        repository.deleteById(roleId);
    }

    @POST
    public Role addRole(@BeanParam Role role){
        return repository.save(role);
    }

    @PUT
    public Role updateRole(@BeanParam Role role){
        return ResourceUtil.updateResource(role, role.getRoleId(), repository);
    }

    public RoleRepository getRepository() {
        return repository;
    }

    public void setRepository(RoleRepository repository) {
        this.repository = repository;
    }
}
