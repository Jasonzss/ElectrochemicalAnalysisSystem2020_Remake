package com.bluedot.infrastructure.repository;

import com.bluedot.domain.rbac.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 9:27
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
