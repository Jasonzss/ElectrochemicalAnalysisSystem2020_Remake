package com.bluedot.infrastructure.repository;

import com.bluedot.domain.rbac.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 9:26
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "select * from role " +
            "left join user_role ur on role.role_id = ur.role_id " +
            "left join user u on ur.email = u.email " +
            "where u.email = ?1",
            nativeQuery = true)
    List<Role> findRolesByEmail(String email);
}
