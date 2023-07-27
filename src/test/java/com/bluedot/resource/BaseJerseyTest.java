package com.bluedot.resource;

import cn.hutool.core.date.DateUtil;
import com.bluedot.application.CaptchaDiagramService;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.jax_rs.CustomExceptionMapper;
import com.bluedot.infrastructure.jax_rs.LeastExceptionMapper;
import com.bluedot.resource.vo.UserForm;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.internal.MultiPartWriter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

/**
 * @author Jason
 * @since 2023/07/27 - 15:21
 */
public class BaseJerseyTest extends JerseyTest {
    public static final Logger log = LoggerFactory.getLogger(BaseJerseyTest.class);

    private final CaptchaDiagramService service = CaptchaDiagramService.getCaptchaDiagramService();

    /**
     * 测试专用用户数据
     */
    protected final static String EMAIL = "54321@test.com";
    protected final static String USERNAME = "测试名称";
    protected final static String BIRTHDAY = DateUtil.now();
    protected final static String TEL = "10086";
    protected final static String PASSWORD = "123456aa.";
    protected final static String SEX = "男";
    protected static UserForm user;

    static {
        user = new UserForm();
        user.setEmail(EMAIL);
        user.setUsername(USERNAME);
        user.setBirthday(BIRTHDAY);
        user.setTel(TEL);
        user.setPassword(PASSWORD);
        user.setSex(SEX);
    }

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("com.bluedot");
        resourceConfig.register(MultiPartFeature.class);
        resourceConfig.register(UserResource.class);
        resourceConfig.register(CustomExceptionMapper.class);
        resourceConfig.register(LeastExceptionMapper.class);
        return resourceConfig;
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(MultiPartFeature.class);
    }

    /**
     * 通过验证码
     * @return 验证通过的凭证，存入cookie中
     */
    protected String verify(){
        Response response = target("captcha-diagram").request().get();
        String captchaId = response.getCookies().get("captcha-id").getValue();
        String verifyCode = service.getVerifyCode(captchaId);

        final Form form = new Form();
        form.param("verify-code", verifyCode);

        Response post = target("captcha-diagram").request()
                .cookie(new NewCookie("captcha-id", captchaId))
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        log.info(post.getEntity().toString());
        return captchaId;
    }

    /**
     * 以超管身份登录
     * @return 登录成功的凭证
     */
    protected Cookie loginAsSuperManager(){
        String cookie = verify();
        NewCookie newCookie = new NewCookie("captcha-id", cookie);

        final Form form = new Form();
        form.param("email", EMAIL);
        form.param("password", PASSWORD);
        form.param("remember-me", "true");


        Response post = target("session").request()
                .cookie(newCookie)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        log.info(post.getEntity().toString());

        return newCookie;
    }
}
