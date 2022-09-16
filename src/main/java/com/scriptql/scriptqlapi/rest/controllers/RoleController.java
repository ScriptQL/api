package com.scriptql.scriptqlapi.rest.controllers;

import com.scriptql.scriptqlapi.domain.entities.Role;
import com.scriptql.scriptqlapi.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/role")
public class RoleController {

    private RoleService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role create(@RequestBody Role role) {
        return this.service.create(role);
    }

    @GetMapping
    public List<Role> findAll() {
        return this.service.findAll();
    }

    @GetMapping("{id}")
    public Role findById(@PathVariable long id) {
        return this.service.findById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.service.delete(id);
    }
}
