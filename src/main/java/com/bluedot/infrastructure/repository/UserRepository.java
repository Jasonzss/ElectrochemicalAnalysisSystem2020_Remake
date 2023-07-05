package com.bluedot.infrastructure.repository;

import com.bluedot.domain.rbac.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 14:33
 * @Description ：
 */
public interface UserRepository extends JpaRepository<User, String> {
}
