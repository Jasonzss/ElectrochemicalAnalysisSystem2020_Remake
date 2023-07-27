package com.bluedot.resource;

import com.bluedot.TestUtil;
import com.bluedot.resource.vo.UserForm;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * @author Jason
 * @since 2023/07/27 - 17:38
 */
public class SessionResourceTest extends BaseJerseyTest {
    final String path = "session";

    @Test
    public void testLogin(){
        String cookie = verify();

        final Form form = new Form();
        form.param("email", EMAIL);
        form.param("password", PASSWORD);
        form.param("remember-me", "true");

        Response post = target(path).request()
                .cookie(new NewCookie("captcha-id", cookie))
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        TestUtil.printResponse(post);
    }
}
