package com.scriptql.api.rest.controllers;

import com.scriptql.api.domain.PagedResponse;
import com.scriptql.api.domain.Paginator;
import com.scriptql.api.domain.entities.Role;
import com.scriptql.api.domain.entities.User;
import com.scriptql.api.domain.errors.SecurityError;
import com.scriptql.api.domain.request.EditUserRequest;
import com.scriptql.api.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public PagedResponse<User> search(User request, Paginator paginator) {
        return this.service.search(request, paginator);
    }

    @GetMapping("/{id:\\d+}")
    public User findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @PatchMapping("/{id:\\d+}")
    public User edit(@PathVariable("id") long id, @RequestBody EditUserRequest request) throws SecurityError {
        return this.service.edit(id, request);
    }

    @GetMapping("/{id:\\d+}/roles")
    public List<Role> getRoles(@PathVariable("id") long id) {
        return this.service.getRoles(id);
    }

}
