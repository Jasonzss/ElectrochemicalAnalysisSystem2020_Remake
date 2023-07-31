package com.bluedot.resource;

import com.bluedot.application.UserService;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.repository.UserRepository;
import com.bluedot.infrastructure.shiro.Auth;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;
import com.bluedot.resource.vo.UploadFile;
import com.bluedot.resource.vo.UserForm;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author Jason
 * @since 2023/05/28 - 14:32
 */
@Path("users")
@Produces({"application/json;charset=UTF-8"})
public class UserResource {
    @Inject
    private UserRepository repository;

    @Inject
    private UserService userService;

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("email") final String email){
        Optional<User> user = repository.findById(email);
        return user.orElse(null);
    }

    @DELETE
    @Path("{email}")
    public void deleteUser(@PathParam("email") final String email){
        repository.deleteById(email);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User registerUser(@BeanParam UserForm form, @FormParam("registry-code") String registryCode){
        return userService.registerUser(form, registryCode);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(@BeanParam UserForm form){
        return userService.updateUser(form);
    }

    @GET
    @Path("pages")
    public PageData<User> getUserPage(@BeanParam PageInfo pageInfo){
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }

    /**
     * 用户头像的查询是属于用户资源的子资源，所以将用户头像相关方法移入这里
     * @param fileData 图片文件相关信息
     * @param fileStream 图片文件IO流
     * @param subject shiro认证对象
     */
    @PUT
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Path("img")
    public String updateUserImg(@FormDataParam("file") InputStream fileStream,
                              @FormDataParam("file") FormDataContentDisposition fileData,
                              @Auth Subject subject){

        return userService.updateUserImg((String) subject.getPrincipal(),
                new UploadFile(fileData, fileStream));
    }

    @Path("registry-email")
    @POST
    public String sendRegistryEmail(@Auth Subject subject){
        userService.sendRegistryEmail((String) subject.getPrincipal());
        return "邮件已发送，请及时查收";
    }

    public UserRepository getRepository() {
        return repository;
    }

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
