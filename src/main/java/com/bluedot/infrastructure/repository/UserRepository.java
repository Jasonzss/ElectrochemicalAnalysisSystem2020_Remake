package com.bluedot.infrastructure.repository;

import com.bluedot.domain.rbac.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 14:33
 * @Description ：
 */
public interface UserRepository extends JpaRepository<User, String> {
    @Modifying
    @Query("update User u set u.userImg = ?1 where u.email = ?2")
    void saveUserImage(String uri, String email);

    User findSaltByEmail(String email);
}
