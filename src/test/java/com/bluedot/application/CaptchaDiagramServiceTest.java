package com.bluedot.application;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Jason
 * @since 2023/07/27 - 16:21
 */
public class CaptchaDiagramServiceTest {
    CaptchaDiagramService service = CaptchaDiagramService.getCaptchaDiagramService();

    @Test
    public void test() throws IOException {
        BufferedImage captchaDiagram = service.getCaptchaDiagram(UUID.randomUUID());
        File file = new File("src\\test\\resources\\img\\captcha.png");
        ImageIO.write(captchaDiagram, "png", file);
        file.deleteOnExit();
    }
}
