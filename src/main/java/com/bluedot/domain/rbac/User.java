package com.bluedot.domain.rbac;

import com.bluedot.infrastructure.json.ResponseEntity;
import com.bluedot.infrastructure.json.ResponseIgnore;
import com.bluedot.infrastructure.json.adapter.DateInChineseUnitTypeAdapter;
import com.bluedot.infrastructure.json.adapter.FestivalTypeAdapter;
import com.bluedot.infrastructure.repository.converter.GenderConverter;
import com.bluedot.infrastructure.repository.enumeration.UserStatus;
import com.google.gson.annotations.JsonAdapter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 13:43
 * @Description ：
 */
@Entity
@Table(name = "user")
public class User implements ResponseEntity {
    public static final String GENDER_BOY = "男";
    public static final String GENDER_GIRL = "女";

    public static final Integer BOY_DB = 1;
    public static final Integer GIRL_DB = 0;

    @Id
    @Email(message = "非法邮箱")
    private String email;

    @Column(nullable = false)
    @ResponseIgnore
    @Length(min = 6, max = 20, message = "密码长度应在6~20之间")
    private String password;

    @Column(nullable = false)
    @ResponseIgnore
    private String salt;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_status", nullable = false)
    private UserStatus userStatus;

    @Column(nullable = false)
    @NotNull
    @Length(min = 2, max = 20, message = "用户昵称长度应在2~20之间")
    private String username;

    @Convert(converter = GenderConverter.class)
    private String sex;

    @JsonAdapter(FestivalTypeAdapter.class)
    @Past
    private Date birthday;

    @Length(min = 11, max = 11)
    private Long tel;

    @Column(name = "user_img", nullable = false)
    private String userImg;

    @CreationTimestamp
    @Column(name = "registration_date")
    @JsonAdapter(DateInChineseUnitTypeAdapter.class)
    private Date registrationDate;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

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
