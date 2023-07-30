package com.bluedot.domain.rbac;

import com.bluedot.infrastructure.json.ResponseEntity;

import javax.persistence.*;
import javax.ws.rs.FormParam;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 13:43
 * @Description ：
 */
@Entity
@Table(name = "permission")
public class Permission implements ResponseEntity {
    @Id
    @GeneratedValue
    @Column(name = "permission_id")
    @FormParam("permission-id")
    private Integer permissionId;

    @Column(name = "permission_name", nullable = false, unique = true)
    @FormParam("permission-name")
    private String permissionName;

    @FormParam("description")
    private String description;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
