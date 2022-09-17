package com.scriptql.scriptqlapi.rest.controllers;

import com.scriptql.scriptqlapi.rest.mappers.request.DatabaseConnectionRequestMapper;
import com.scriptql.scriptqlapi.domain.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.rest.mappers.response.DatabaseConnectionResponseMapper;
import com.scriptql.scriptqlapi.services.DatabaseConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/database_connection")
public class DatabaseConnectionController {

    private DatabaseConnectionService service;

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public DatabaseConnectionResponseMapper create(@RequestBody @Valid DatabaseConnectionRequestMapper databaseConnectionRequestMapper) {
        var databaseConnection = this.service.create(databaseConnectionRequestMapper);

        return DatabaseConnectionResponseMapper.fromDatabaseConnection(databaseConnection);
    }

    @GetMapping
    public List<DatabaseConnectionResponseMapper> findAll() {
        var databaseConnections = this.service.findAll();

        return databaseConnections
                .stream()
                .map(DatabaseConnectionResponseMapper::fromDatabaseConnection)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public DatabaseConnectionResponseMapper findById(@PathVariable long id) {
        var databaseConnection = this.service.findById(id);

        return DatabaseConnectionResponseMapper.fromDatabaseConnection(databaseConnection);
    }

    @PutMapping("{id}")
    public void update(@PathVariable long id, @RequestBody DatabaseConnectionRequestMapper databaseConnectionPayload) {
        this.service.update(id, databaseConnectionPayload);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.service.delete(id);
    }
}
