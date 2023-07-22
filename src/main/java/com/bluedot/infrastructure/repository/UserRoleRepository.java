package com.bluedot.infrastructure.repository;

import com.bluedot.domain.rbac.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 9:42
 */
public interface UserRoleRepository extends JpaRepository<UserRole, UserRole> {
}
