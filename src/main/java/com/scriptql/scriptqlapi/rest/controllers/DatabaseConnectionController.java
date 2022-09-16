package com.scriptql.scriptqlapi.rest.controllers;

import com.scriptql.scriptqlapi.rest.mappers.DatabaseConnectionMapper;
import com.scriptql.scriptqlapi.domain.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.services.DatabaseConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/database_connection")
public class DatabaseConnectionController {

    private DatabaseConnectionService service;

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public DatabaseConnection create(@RequestBody @Valid DatabaseConnectionMapper databaseConnectionMapper) {
        return this.service.create(databaseConnectionMapper);
    }

    @GetMapping
    public List<DatabaseConnection> findAll() {
        return this.service.findAll();
    }

    @GetMapping("{id}")
    public DatabaseConnection findById(@PathVariable long id) {
        return this.service.findById(id);
    }

    @PutMapping("{id}")
    public void update(@PathVariable long id, @RequestBody DatabaseConnectionMapper databaseConnectionPayload) {
        this.service.update(id, databaseConnectionPayload);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.service.delete(id);
    }
}
