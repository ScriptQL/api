package com.scriptql.scriptqlapi.controllers;

import com.scriptql.scriptqlapi.dto.DatabaseConnectionDTO;
import com.scriptql.scriptqlapi.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.services.DatabaseConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

}
