package com.scriptql.scriptqlapi.rest.controllers;

import com.scriptql.scriptqlapi.domain.entities.Role;
import com.scriptql.scriptqlapi.rest.mappers.request.RoleRequestMapper;
import com.scriptql.scriptqlapi.rest.mappers.response.RoleResponseMapper;
import com.scriptql.scriptqlapi.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/role")
public class RoleController {

    private RoleService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponseMapper create(@RequestBody RoleRequestMapper mapper) {
        var role = this.service.create(mapper);

        return RoleResponseMapper.fromRole(role);
    }

    @GetMapping
    public List<RoleResponseMapper> findAll() {
        var roles = this.service.findAll();

        return roles
                .stream()
                .map(RoleResponseMapper::fromRole)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public RoleResponseMapper findById(@PathVariable long id) {
        var role = this.service.findById(id);

        return RoleResponseMapper.fromRole(role);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.service.delete(id);
    }
}
