package com.bluedot.resource;

import com.bluedot.application.CaptchaDiagramService;
import com.bluedot.application.UserService;
import com.bluedot.domain.rbac.exception.UserException;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.shiro.Auth;
import org.apache.shiro.subject.Subject;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 基于shiro默认的认证功能提供的登录，因为shiro是靠内部维持session记录登录信息的所以设定本类为SessionResource
 *
 * @author Jason
 * @creationDate 2023/07/21 - 15:27
 */
@Path("session")
@Produces(MediaType.TEXT_PLAIN)
public class SessionResource {
    @Inject
    private UserService userService;

    @Inject
    private CaptchaDiagramService captchaDiagramService;

    @POST
    public String login(@FormParam("email") String email,
                        @FormParam("password") String password,
                        @DefaultValue ("false") @FormParam("remember-me") boolean rememberMe,
                        @CookieParam("captcha-id") String captchaId){
        if (captchaDiagramService.isCaptchaPassed(captchaId)) {
            userService.login(email, password, rememberMe);
        }else {
            throw new UserException(CommonErrorCode.E_6006);
        }
        return "登陆成功！";
    }

    @DELETE
    public void invalidateHttpSession(@Auth Subject subject){
        subject.logout();
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public CaptchaDiagramService getCaptchaDiagramService() {
        return captchaDiagramService;
    }

    public void setCaptchaDiagramService(CaptchaDiagramService captchaDiagramService) {
        this.captchaDiagramService = captchaDiagramService;
    }
}
