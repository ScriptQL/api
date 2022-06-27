package com.scriptql.scriptqlapi.controllers;

import com.scriptql.scriptqlapi.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.repositories.DatabaseConnectionRepository;
import com.scriptql.scriptqlapi.services.ConnectionManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connection_manager")
public class ConnectionManagerController {
    private final DatabaseConnectionRepository repository;
    private final ConnectionManagerService service;

    public ConnectionManagerController(DatabaseConnectionRepository repository, ConnectionManagerService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping()
    public List<DatabaseConnection> list() {
        return repository.findAll();
    }

    @PostMapping()
    public DatabaseConnection create(@RequestBody DatabaseConnection request) {
        return service.create(request);
    }

    @PatchMapping()
    public DatabaseConnection update(@RequestBody DatabaseConnection request) {
        return service.update(request);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public DatabaseConnection delete(@PathVariable("id") long id) {
        return service.delete(id);
    }
}
