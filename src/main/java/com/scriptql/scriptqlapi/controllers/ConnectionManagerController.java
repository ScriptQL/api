package com.scriptql.scriptqlapi.controllers;

import com.scriptql.scriptqlapi.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.generic.AbstractController;
import com.scriptql.scriptqlapi.services.ConnectionManagerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connection_manager")
public class ConnectionManagerController extends AbstractController<DatabaseConnection> {

    public ConnectionManagerController(ConnectionManagerService service) {
        super(service);
    }

}
