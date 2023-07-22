package com.bluedot.resource;

import com.bluedot.application.UserService;

import javax.inject.Inject;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Jason
 * @creationDate 2023/07/21 - 15:27
 */
@Path("/session")
public class SessionResource {
    @Inject
    private UserService userService;

    @POST
    public void getToken(@FormParam("email") String email, @FormParam("password") String password,
                           @FormParam("remember-me") boolean rememberMe,
                           @CookieParam("captcha-id") String captchaId){
        userService.login(email, password, rememberMe, captchaId);
    }
}
