package com.bluedot.resource;

import com.bluedot.application.UserService;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.repository.UserRepository;
import com.bluedot.resource.vo.UserForm;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 14:32
 * @Description ：
 */
@Path("user")
@Produces({"application/json;charset=UTF-8"})
public class UserResource {
    @Inject
    private UserRepository repository;

    @Inject
    private UserService userService;

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("email") final String email){
        Optional<User> user = repository.findById(email);
        return user.orElse(null);
    }

    @DELETE
    @Path("{email}")
    public void deleteUser(@PathParam("email") final String email){
        repository.deleteById(email);
    }

    @POST
    public void addUser(@BeanParam UserForm form){
        userService.registerUser(form);
    }

    @PUT
    public void updateUser(@BeanParam UserForm form){
        userService.updateUser(form);
    }
}
