package com.bluedot.domain.rbac;

import javax.persistence.*;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 1:49
 * @Description ：
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;
    private String description;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
