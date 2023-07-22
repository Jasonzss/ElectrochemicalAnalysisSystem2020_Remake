package com.bluedot.domain.rbac;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 13:43
 * @Description ：
 */
@Entity
@IdClass(UserRole.class)
@Table(name = "user_role")
public class UserRole implements Serializable {
    @Id
    private String email;
    @Id
    @Column(name = "role_id")
    private Integer roleId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
