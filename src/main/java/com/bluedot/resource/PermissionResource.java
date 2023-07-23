package com.bluedot.resource;

import com.bluedot.domain.rbac.Permission;

import javax.ws.rs.GET;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:14
 */
public class PermissionResource {
    public List<Permission> listPermission(){
        return null;
    }

    @GET
    public Permission getPermission(int permissionId){
        return null;
    }

    public void deletePermission(int permissionId){

    }

    public Permission addPermission(String permissionName, String desc){
        return null;
    }

    public Permission updatePermission(Permission permission){
        return null;
    }
}
