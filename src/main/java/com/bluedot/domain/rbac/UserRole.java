package com.bluedot.domain.rbac;

import com.bluedot.infrastructure.json.ResponseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 13:43
 * @Description ：
 */
@Entity
@IdClass(UserRole.class)
@Table(name = "user_role")
public class UserRole implements Serializable, ResponseEntity {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(email, userRole.email) && Objects.equals(roleId, userRole.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, roleId);
    }
}
