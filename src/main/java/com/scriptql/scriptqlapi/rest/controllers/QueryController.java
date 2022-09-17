package com.scriptql.scriptqlapi.rest.controllers;

import com.scriptql.scriptqlapi.rest.mappers.request.QueryRequestMapper;
import com.scriptql.scriptqlapi.domain.entities.Query;
import com.scriptql.scriptqlapi.services.QueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/query")
public class QueryController {

    private QueryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Query create(@RequestBody @Valid QueryRequestMapper mapper) {
        return this.service.create(mapper);
    }
}
