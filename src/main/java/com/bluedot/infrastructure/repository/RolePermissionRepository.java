package com.bluedot.infrastructure.repository;

import com.bluedot.domain.rbac.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 9:27
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermission> {
}
