package com.scriptql.scriptqlapi.rest.controllers;

import com.scriptql.scriptqlapi.domain.entities.User;
import com.scriptql.scriptqlapi.rest.mappers.UserMapper;
import com.scriptql.scriptqlapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserMapper userMapper) {
        return service.create(userMapper);
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public User findById(@PathVariable long id) {
        return service.findById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
