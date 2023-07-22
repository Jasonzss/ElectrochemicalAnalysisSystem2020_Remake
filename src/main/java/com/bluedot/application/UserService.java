package com.bluedot.application;

import cn.hutool.jwt.JWTUtil;
import com.bluedot.domain.file.model.UserImageFile;
import com.bluedot.domain.rbac.User;
import com.bluedot.domain.rbac.exception.UserException;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.UserRepository;
import com.bluedot.infrastructure.repository.enumeration.UserStatus;
import com.bluedot.infrastructure.shiro.RetryLimitHashedCredentialsMatcher;
import com.bluedot.infrastructure.utils.PojoUtil;
import com.bluedot.resource.vo.UserForm;
import org.apache.commons.fileupload.FileItem;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.CookieParam;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 14:32
 * @Description ：
 */
@Component
public class UserService {
    private static final String DEFAULT_USER_IMG_URL = "user_img/default/userImg.jpg";
    private static final String PASSWORD_ENCRYPT_ALGO = "md5";
    private static final Integer HASH_ITERATIONS = 1;

    @Inject
    private Subject subject;

    @Inject
    private UserRepository repository;

    @Inject
    private CaptchaDiagramService captchaDiagramService;

    public void registerUser(UserForm form){
        User user = form.getUserFromForm();
        String password = user.getPassword();

        //加密密码
        String salt = ByteSource.Util.bytes(user.getEmail() + UUID.randomUUID().toString()).toBase64().substring(0, 6);
        String pwd = new SimpleHash(PASSWORD_ENCRYPT_ALGO, password, salt, HASH_ITERATIONS).toHex();
        user.setPassword(pwd);
        user.setSalt(salt);
        user.setUserStatus(UserStatus.NORM);

        //设置并保存用户头像
        FileItem userImg = form.getUserImgFileItem();
        if (!repository.existsById(user.getEmail())){
            try {
                if(userImg == null){
                    user.setUserImg(DEFAULT_USER_IMG_URL);
                }else {
                    UserImageFile file = new UserImageFile(user.getEmail());
                    file.updateFile(userImg.getInputStream());
                    user.setUserImg(file.getFileUri());
                }

                //保存用户
                repository.save(user);
            } catch (IOException e) {
                throw new UserException(CommonErrorCode.E_6001);
            }
        }else {
            throw new UserException(CommonErrorCode.E_6002);
        }
    }

    public void updateUser(UserForm form){
        User user = form.getUserFromForm();

        Optional<User> byId = repository.findById(user.getEmail());
        if (byId.isPresent()) {
            User u = byId.get();
            PojoUtil.updatePojo(user, u);
            repository.save(u);
        }else {
            throw new UserException(CommonErrorCode.E_6003);
        }
    }

    public void login(String email, String password, boolean rememberMe, String captchaId){
        if (captchaDiagramService.isCaptchaPassed(captchaId)) {
            try{
                UsernamePasswordToken token = new UsernamePasswordToken(email, password);
                token.setRememberMe(rememberMe);
                subject.login(token);
            }catch (AuthenticationException e){
                throw new UserException(CommonErrorCode.E_6005);
            }
        }else {
            throw new UserException(CommonErrorCode.E_6006);
        }
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public UserRepository getRepository() {
        return repository;
    }

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}
