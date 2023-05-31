package com.bluedot.domain.rbac;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 13:43
 * @Description ：
 */
@Entity
public class User {
    @Id
    private String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
