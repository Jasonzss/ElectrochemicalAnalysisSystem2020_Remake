package com.bluedot.infrastructure.repository;

import com.bluedot.domain.rbac.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 9:26
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
