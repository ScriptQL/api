package com.scriptql.scriptqlapi.rest.controllers;

import com.scriptql.scriptqlapi.domain.entities.User;
import com.scriptql.scriptqlapi.rest.mappers.request.UserRequestMapper;
import com.scriptql.scriptqlapi.rest.mappers.response.UserResponseMapper;
import com.scriptql.scriptqlapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseMapper create(@RequestBody @Valid UserRequestMapper mapper) {
        var user = service.create(mapper);

        return UserResponseMapper.fromUser(user);
    }

    @GetMapping
    public List<UserResponseMapper> findAll() {
        var users = service.findAll();

        return users
                .stream()
                .map(UserResponseMapper::fromUser)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public UserResponseMapper findById(@PathVariable long id) {
        var user = service.findById(id);

        return UserResponseMapper.fromUser(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
