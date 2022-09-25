package com.scriptql.api.rest.controllers;

import com.scriptql.api.domain.PagedResponse;
import com.scriptql.api.domain.Paginator;
import com.scriptql.api.domain.entities.DatabaseConnection;
import com.scriptql.api.domain.entities.Role;
import com.scriptql.api.domain.request.CreateConnectionRequest;
import com.scriptql.api.services.ConnectionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("connections")
public class ConnectionController {

    private final ConnectionService service;

    public ConnectionController(ConnectionService service) {
        this.service = service;
    }

    @GetMapping
    public PagedResponse<DatabaseConnection> search(DatabaseConnection request, Paginator paginator) {
        return this.service.search(request, paginator);
    }

    @GetMapping("/{id:\\d+}")
    public DatabaseConnection findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DatabaseConnection create(@RequestBody @Valid CreateConnectionRequest request) {
        return this.service.create(request);
    }

    @PatchMapping("/{id:\\d+}")
    public DatabaseConnection edit(@PathVariable("id") long id, @RequestBody CreateConnectionRequest request) {
        return this.service.edit(id, request);
    }

    @GetMapping("/{id:\\d+}/reviewers")
    public List<Role> getReviewers(@PathVariable("id") long id) {
        return this.service.getReviewers(id);
    }

}
