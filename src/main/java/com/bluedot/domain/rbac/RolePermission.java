package com.bluedot.domain.rbac;

import com.bluedot.infrastructure.json.ResponseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 13:44
 * @Description ：
 */
@Entity
@Table(name = "role_permission")
@IdClass(RolePermission.class)
public class RolePermission implements ResponseEntity, Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return Objects.equals(roleId, that.roleId) && Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId);
    }

    @Override
    public String toString() {
        return "RolePermission{" +
                "roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}
