package com.bluedot.resource;

import com.bluedot.application.CaptchaDiagramService;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.util.UUID;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 11:37
 */
@Path("captcha-diagram")
public class CaptchaDiagramResource {
    @Inject
    private CaptchaDiagramService service;

    @GET
    public Response getCaptchaDiagram(){
        UUID uuid = UUID.randomUUID();
        Image captchaDiagram = service.getCaptchaDiagram(uuid);
        NewCookie newCookie = new NewCookie("captcha-id", uuid.toString());

        return Response.ok((StreamingOutput) output ->
                ImageIO.write((RenderedImage) captchaDiagram, "jpg", output))
                .header("Content-disposition", "attachment;filename=captcha-diagram.jpg")
                .header("Cache-Control", "no-cache")
                .status(200)
                .cookie(newCookie)
                .build();
    }

    @DELETE
    public Response verifyCaptchaDiagram(@FormParam("verify-code") String code,
                                         @CookieParam("captcha-id") String captchaId){
        if(service.verifyCode(code, captchaId)){
            return Response.status(200).build();
        }
        return Response.status(400).entity("验证码输入错误").type(MediaType.APPLICATION_JSON).build();
    }

    public CaptchaDiagramService getService() {
        return service;
    }

    public void setService(CaptchaDiagramService service) {
        this.service = service;
    }
}
