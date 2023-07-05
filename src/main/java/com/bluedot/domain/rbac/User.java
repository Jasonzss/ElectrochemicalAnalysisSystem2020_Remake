package com.bluedot.domain.rbac;

import com.bluedot.infrastructure.repository.converter.GenderConverter;
import com.bluedot.infrastructure.repository.converter.PasswordConverter;
import com.bluedot.infrastructure.repository.enumeration.UserStatus;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 13:43
 * @Description ：
 */
@Entity
@Table(name = "user")
public class User {
    public static final String GENDER_BOY = "男";
    public static final String GENDER_GIRL = "女";

    public static final Integer BOY_DB = 1;
    public static final Integer GIRL_DB = 0;

    @Id
    private String email;

    @Convert(converter = PasswordConverter.class)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String salt;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_status", nullable = false)
    private UserStatus userStatus;

    @Column(nullable = false)
    private String username;

    @Convert(converter = GenderConverter.class)
    private String sex;

    private Date birthday;

    private Long tel;

    @Column(name = "user_img", nullable = false)
    private String userImg;

    @CreatedDate
    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", userStatus=" + userStatus +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", tel=" + tel +
                ", userImg='" + userImg + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
