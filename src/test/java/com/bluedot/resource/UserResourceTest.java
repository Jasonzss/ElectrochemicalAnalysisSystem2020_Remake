package com.bluedot.resource;

import cn.hutool.core.date.DateUtil;
import com.bluedot.TestUtil;
import com.bluedot.infrastructure.jax_rs.CustomExceptionMapper;
import com.bluedot.infrastructure.jax_rs.LeastExceptionMapper;
import com.bluedot.resource.vo.UserForm;
import org.glassfish.jersey.media.multipart.*;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.File;

/**
 * @author Jason
 * @creationDate 2023/07/21 - 17:37
 */
public class UserResourceTest extends BaseJerseyTest {
    final String path = "users";

    @Test
    public void testGet(){
        final Builder request = target(path).path("2418972236@qq.com").request();
        Response response = request.get();
        String s = response.readEntity(String.class);
        log.info(s);
    }

    @Test
    public void testRegisterUser(){
        final Form form = new Form();
        form.param(UserForm.EMAIL, EMAIL);
        form.param(UserForm.USERNAME, USERNAME);
        form.param(UserForm.BIRTHDAY, BIRTHDAY);
        form.param(UserForm.TEL, TEL);
        form.param(UserForm.PASSWORD, PASSWORD);
        form.param(UserForm.SEX, SEX);

        Response post = target(path).request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        TestUtil.printResponse(post);
    }

    @Test
    public void testGetUserPage(){
        Response response = target(path).path("/pages").queryParam("page", 0)
                .queryParam("limit", 2)
                .request()
                .get();
        TestUtil.printResponse(response);
    }

    @Test
    public void testUpdateUserImg(){
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

        TestUtil.printResponse(response);
    }
}
