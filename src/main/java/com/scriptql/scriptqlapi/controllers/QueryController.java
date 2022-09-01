package com.scriptql.scriptqlapi.controllers;

import com.scriptql.scriptqlapi.entities.Query;
import com.scriptql.scriptqlapi.generic.AbstractController;
import com.scriptql.scriptqlapi.services.QueryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/query")
public class QueryController extends AbstractController<Query> {

    public QueryController(QueryService service) {
        super(service);
    }

    @Override
    @DeleteMapping("/{id:[0-9]+}")
    public void delete(@Valid @PathVariable("id") long id) {
        throw new UnsupportedOperationException();
    }

}
