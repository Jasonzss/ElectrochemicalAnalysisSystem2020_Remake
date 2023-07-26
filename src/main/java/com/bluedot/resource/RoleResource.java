package com.bluedot.resource;

import com.bluedot.domain.rbac.Role;

import javax.ws.rs.*;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:13
 */
@Path("roles")
public class RoleResource {
    public List<Role> listRole(){
        return null;
    }

    @GET
    @Path("{id}")
    public Role getRole(@PathParam("id") int roleId){
        return null;
    }

    public void deleteRole(int roleId){

    }

    public Role addRole(String roleName, String roleDesc){
        return null;
    }

    public Role updateRole(Role role){
        return null;
    }
}
