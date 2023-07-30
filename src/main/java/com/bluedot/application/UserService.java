package com.bluedot.application;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bluedot.domain.file.model.UserImageFile;
import com.bluedot.domain.rbac.User;
import com.bluedot.domain.rbac.exception.UserException;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.UserRepository;
import com.bluedot.infrastructure.repository.enumeration.UserStatus;
import com.bluedot.resource.vo.UploadFile;
import com.bluedot.resource.vo.UserForm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.InputStream;
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

    /**
     * 注册用户
     * @param form 用户注册表单
     * @return 注册成功后的用户
     */
    public User registerUser(UserForm form){
        User user = form.getUserFromForm();
        String password = user.getPassword();

        //加密密码
        String salt = ByteSource.Util.bytes(user.getEmail() + UUID.randomUUID().toString()).toBase64().substring(0, 6);
        String pwd = new SimpleHash(PASSWORD_ENCRYPT_ALGO, password, salt, HASH_ITERATIONS).toHex();
        user.setPassword(pwd);
        user.setSalt(salt);
        user.setUserStatus(UserStatus.NORM);

        //设置并保存用户头像
        UploadFile userImg = form.getUploadFile();
        if (!repository.existsById(user.getEmail())){
            if(userImg == null){
                user.setUserImg(DEFAULT_USER_IMG_URL);
            }else {
                UserImageFile file = new UserImageFile(user.getEmail());
                file.updateFile(userImg.getUserImgStream());
                user.setUserImg(file.getFileUri());
            }

            //保存用户
            return repository.save(user);
        }else {
            throw new UserException(CommonErrorCode.E_6002);
        }
    }

    public User updateUser(UserForm form){
        User user = form.getUserFromForm();

        Optional<User> byId = repository.findById(user.getEmail());
        if (byId.isPresent()) {
            User u = byId.get();
            BeanUtil.copyProperties(user, u, CopyOptions.create(User.class, true));
            return repository.save(u);
        }else {
            throw new UserException(CommonErrorCode.E_6003);
        }
    }

    public void login(String email, String password, boolean rememberMe){
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(email, password);
            token.setRememberMe(rememberMe);
            subject.login(token);
        }catch (AuthenticationException e){
            throw new UserException(CommonErrorCode.E_6005);
        }
    }

    /**
     * 修改用户头像
     * <p>
     *     使用Repository的自定义修改方法需要加上注解 Transactional
     * </p>
     * @param email 邮箱
     * @param userImg 修改后的头像图片文件
     * @return 修改后图片的uri
     */
    @Transactional
    public String updateUserImg(String email, UploadFile userImg) {
        InputStream userImgStream = userImg.getUserImgStream();
        if(userImgStream != null){
            UserImageFile imageFile = new UserImageFile(email);
            imageFile.updateFile(userImgStream);
            String uri = imageFile.getFileUri();

            repository.saveUserImage(uri, email);
            return uri;
        }
        throw new UserException(CommonErrorCode.E_6008);
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
