package com.scriptql.scriptqlapi.controllers;

import com.scriptql.scriptqlapi.entities.Role;
import com.scriptql.scriptqlapi.exceptions.role.RoleNotFoundException;
import com.scriptql.scriptqlapi.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            return this.service.findById(id);
        } catch (RoleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        try {
            this.service.delete(id);
        } catch (RoleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
