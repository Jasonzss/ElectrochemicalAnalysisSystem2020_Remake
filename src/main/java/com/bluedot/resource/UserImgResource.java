package com.bluedot.resource;

import com.bluedot.application.UserService;
import com.bluedot.infrastructure.shiro.Auth;
import com.bluedot.resource.vo.UploadFile;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * @author Jason
 * @creationDate 2023/07/21 - 17:12
 */
public class UserImgResource {
    @Inject
    private UserService userService;

    @PUT
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public void updateUserImg(@FormDataParam("user-img") FormDataContentDisposition fileData,
                              @FormDataParam("user-img") InputStream fileStream,
                              @Auth Subject subject){
        userService.updateUserImg((String) subject.getPrincipal(), new UploadFile(fileData, fileStream));
    }
}
