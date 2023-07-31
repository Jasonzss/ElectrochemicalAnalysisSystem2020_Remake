package com.bluedot.resource.vo;

import cn.hutool.core.date.DateUtil;
import com.bluedot.domain.rbac.User;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Encoded;
import javax.ws.rs.FormParam;
import java.io.InputStream;

/**
 * @author Jason
 * @creationDate 2023/07/21 - 15:38
 */
public class UserForm {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String USERNAME = "username";
    public static final String SEX = "sex";
    public static final String BIRTHDAY = "birthday";
    public static final String TEL = "tel";
    public static final String USER_IMG = "user-img";

    @FormParam(EMAIL)
    private String email;
    @FormParam(PASSWORD)
    @Encoded
    private String password;
    @FormParam(USERNAME)
    private String username;
    @FormParam(SEX)
    private String sex;
    @FormParam(BIRTHDAY)
    private String birthday;
    @FormParam(TEL)
    private String tel;

    @FormDataParam("user-img")
    private FormDataContentDisposition userImg;

    @FormDataParam("user-img")
    private InputStream userImgStream;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public FormDataContentDisposition getUserImg() {
        return userImg;
    }

    public void setUserImg(FormDataContentDisposition userImg) {
        this.userImg = userImg;
    }

    public InputStream getUserImgStream() {
        return userImgStream;
    }

    public void setUserImgStream(InputStream userImgStream) {
        this.userImgStream = userImgStream;
    }

    public UploadFile getUploadFile(){
        return userImgStream == null ? null : new UploadFile(userImg, userImgStream);
    }

    public User getUserFromForm(){
        User u = new User();
        u.setEmail(email);
        u.setPassword(password);
        u.setUsername(username);
        u.setSex(sex);
        u.setBirthday(DateUtil.parse(birthday));
        u.setTel(Long.getLong(tel));
        return u;
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", tel='" + tel + '\'' +
                ", userImg=" + userImg +
                '}';
    }
}
