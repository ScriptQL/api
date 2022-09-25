package com.scriptql.api.rest.controllers;

import com.scriptql.api.domain.entities.Token;
import com.scriptql.api.domain.errors.SecurityError;
import com.scriptql.api.domain.request.LoginRequest;
import com.scriptql.api.domain.request.RegisterRequest;
import com.scriptql.api.services.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequest request) throws SecurityError {
        return service.login(request);
    }

    @PostMapping("/register")
    public Token register(@RequestBody RegisterRequest request) throws SecurityError {
        return this.service.register(request);
    }

}
