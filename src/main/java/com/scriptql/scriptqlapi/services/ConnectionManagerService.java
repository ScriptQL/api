package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.repositories.DatabaseConnectionRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import com.scriptql.scriptqlapi.utils.entities.DatabaseConnection;
import org.springframework.stereotype.Service;

@Service
public class ConnectionManagerService extends AbstractService<DatabaseConnection> {

    public ConnectionManagerService(DatabaseConnectionRepository repository, Snowflake snowflake) {
        super(repository, snowflake);
    }

}
