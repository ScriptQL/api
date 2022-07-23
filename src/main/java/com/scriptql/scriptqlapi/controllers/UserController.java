package com.scriptql.scriptqlapi.controllers;

import com.scriptql.scriptqlapi.services.UserService;
import com.scriptql.scriptqlapi.entities.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<User> {

    public UserController(UserService service) {
        super(service);
    }

}
