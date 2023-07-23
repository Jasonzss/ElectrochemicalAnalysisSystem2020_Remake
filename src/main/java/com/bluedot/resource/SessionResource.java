package com.bluedot.resource;

import com.bluedot.application.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;

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
                           @DefaultValue ("false") @FormParam("remember-me") boolean rememberMe,
                           @CookieParam("captcha-id") String captchaId){
        userService.login(email, password, rememberMe, captchaId);
    }
}
