package com.scriptql.scriptqlapi.controllers;

import com.scriptql.scriptqlapi.services.QueryService;
import com.scriptql.scriptqlapi.utils.entities.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query")
public class QuerryController extends AbstractController<Query> {

    public QuerryController(QueryService service) {
        super(service);
    }

}
