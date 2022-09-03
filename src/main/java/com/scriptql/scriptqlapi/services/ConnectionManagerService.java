package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.abstractions.AbstractService;
import com.scriptql.scriptqlapi.repositories.DatabaseConnectionRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import org.springframework.stereotype.Service;

@Service
public class ConnectionManagerService extends AbstractService<DatabaseConnection> {

    public ConnectionManagerService(DatabaseConnectionRepository repository, Snowflake snowflake) {
        super(repository, snowflake);
    }

}
