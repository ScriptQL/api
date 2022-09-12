package com.scriptql.scriptqlapi.controllers;

import com.scriptql.scriptqlapi.dto.DatabaseConnectionDTO;
import com.scriptql.scriptqlapi.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.exceptions.DatabaseConnectionNotFoundException;
import com.scriptql.scriptqlapi.services.DatabaseConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/database_connection")
public class DatabaseConnectionController {

    private DatabaseConnectionService service;

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public DatabaseConnection create(@RequestBody @Valid DatabaseConnectionDTO databaseConnectionDTO) {
        return this.service.create(databaseConnectionDTO);
    }

    @GetMapping
    public List<DatabaseConnection> findAll() {
        return this.service.findAll();
    }

    @GetMapping("{id}")
    public DatabaseConnection findById(@PathVariable long id) {
        try {
            return this.service.findById(id);
        } catch (DatabaseConnectionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("{id}")
    public void update(@PathVariable long id, @RequestBody DatabaseConnectionDTO databaseConnectionPayload) {
        try {
            this.service.update(id, databaseConnectionPayload);
        } catch (DatabaseConnectionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        try {
            this.service.delete(id);
        } catch (DatabaseConnectionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
