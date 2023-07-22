package com.bluedot.resource;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.bluedot.TestUtil;
import com.bluedot.infrastructure.jax_rs.CustomExceptionMapper;
import com.bluedot.infrastructure.jax_rs.LeastExceptionMapper;
import com.bluedot.resource.vo.UserForm;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Jason
 * @creationDate 2023/07/21 - 17:37
 */
public class UserResourceTest extends JerseyTest {
    private static final Logger log = LoggerFactory.getLogger(UserResourceTest.class);
    final String path = "user";

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        ResourceConfig resourceConfig = new ResourceConfig(UserResource.class, CustomExceptionMapper.class, LeastExceptionMapper.class);
        resourceConfig.packages("com.bluedot");
        return resourceConfig;
    }

    @Test
    public void testGet(){
        final Builder request = target(path).path("2418972236@qq.com").request();
        Response response = request.get();
        String s = response.readEntity(String.class);
        log.info(s);
    }

    @Test
    public void testPost(){
        final Form form = new Form();
        form.param(UserForm.EMAIL, "54321@test.com");
        form.param(UserForm.USERNAME, "测试名称");
        form.param(UserForm.BIRTHDAY, DateUtil.now());
        form.param(UserForm.TEL, "10086");
        form.param(UserForm.PASSWORD, "123456aa.");
        form.param(UserForm.SEX, "男");

        Response post = target(path).request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        TestUtil.printResponse(post);
    }

    @Test
    public void testPut(){

    }

}
