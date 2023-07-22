package com.bluedot.domain.rbac;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 13:44
 * @Description ：
 */
@Entity
@Table(name = "role_permission")
@IdClass(RolePermission.class)
public class RolePermission implements Serializable {
    @Id
    @Column(name = "role_id")
    private Integer roleId;
    @Id
    @Column(name = "permission_id")
    private Integer permissionId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}
