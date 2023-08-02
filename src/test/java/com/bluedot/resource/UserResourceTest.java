package com.bluedot.resource;

import com.bluedot.TestUtils;
import com.bluedot.resource.vo.UserInfo;
import org.glassfish.jersey.media.multipart.*;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.*;
import java.io.File;

/**
 * @author Jason
 * @creationDate 2023/07/21 - 17:37
 */
public class UserResourceTest extends BaseJerseyTest {
    final String path = "users";

    @Test
    public void testGet() {
        Cookie cookie = loginAsSuperManager();

        final Builder request = target(path).path(EMAIL).request().cookie(cookie);
        Response response = request.get();
        String s = response.readEntity(String.class);
        log.info(s);
    }

    /**
     * 需要获取邮箱验证码才能注册
     */
    @Test
    public void testRegisterUser() {
        Cookie cookie = loginAsSuperManager();
        File img = new File("src\\test\\resources\\hanako.png");

        FileDataBodyPart filePart = new FileDataBodyPart("file", img);
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();

        formDataMultiPart.field(UserInfo.EMAIL,      I_EMAIL     );
        formDataMultiPart.field(UserInfo.USERNAME,   I_USERNAME  );
        formDataMultiPart.field(UserInfo.BIRTHDAY,   I_BIRTHDAY  );
        formDataMultiPart.field(UserInfo.TEL,        I_TEL       );
        formDataMultiPart.field(UserInfo.PASSWORD,   I_PASSWORD  );
        formDataMultiPart.field(UserInfo.SEX,        I_SEX       );
        FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.bodyPart(filePart);

        Response post = target(path).request().cookie(cookie).post(Entity.entity(multipart, multipart.getMediaType()));
        TestUtils.printResponse(post);
    }

    @Test
    public void testGetUserPage() {
        Cookie cookie = loginAsSuperManager();
        Response response = target(path).path("/pages").queryParam("page", 0)
                .queryParam("limit", 2)
                .request()
                .cookie(cookie)
                .get();
        TestUtils.printResponse(response);
    }

    @Test
    public void testUpdateUser() {
        Cookie cookie = loginAsSuperManager();

        Form form = new Form();
        form.param("sex", "女");

        Response put = target(path).path(EMAIL).request().cookie(cookie)
                .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        TestUtils.printResponse(put);
    }

    @Test
    public void testUpdateUserImg() {
        Cookie cookie = loginAsSuperManager();

        File img = new File("src\\test\\resources\\hanako.png");

        FileDataBodyPart part = new FileDataBodyPart("file", img);

        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(part);
        multiPart.setContentDisposition(part.getContentDisposition());

        Response response = target(path).path("img")
                .request()
                .cookie(cookie)
                .put(Entity.entity(multiPart, multiPart.getMediaType()));

        TestUtils.printResponse(response);
    }
}
