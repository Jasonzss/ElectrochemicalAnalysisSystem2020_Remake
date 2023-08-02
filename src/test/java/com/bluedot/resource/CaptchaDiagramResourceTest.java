package com.bluedot.resource;

import com.bluedot.TestUtils;
import com.bluedot.application.CaptchaDiagramService;
import org.junit.After;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jason
 * @since 2023/07/27 - 14:53
 */
public class CaptchaDiagramResourceTest extends BaseJerseyTest {
    final String path = "captcha-diagram";

    private final CaptchaDiagramService service = CaptchaDiagramService.getCaptchaDiagramService();

    File file = new File("src\\test\\resources\\img\\captcha.png");

    @After
    public void delete(){
        file.deleteOnExit();
    }

    private String get() throws IOException {
        Response response = target(path).request().get();
        InputStream inputStream = (InputStream) response.getEntity();
        BufferedImage image = ImageIO.read(inputStream);
        ImageIO.write(image, "png", file);
        if (!file.exists()) {
            throw new RuntimeException("获取验证图片异常");
        }

        return response.getCookies().get("captcha-id").getValue();
    }

    @Test
    public void testGetCaptchaDiagram() throws IOException {
        get();
    }

    @Test
    public void testVerifyCaptchaDiagram() throws IOException {
        String captchaId = get();
        String verifyCode = service.getVerifyCode(captchaId);

        final Form form = new Form();
        form.param("verify-code", verifyCode);

        Response post = target(path).request()
                .cookie(new NewCookie("captcha-id", captchaId))
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        TestUtils.printResponse(post);
    }
}
