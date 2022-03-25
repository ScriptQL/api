package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.repositories.DatabaseConnectionRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import org.springframework.stereotype.Service;

@Service
public class ConnectionManagerService {
    private final DatabaseConnectionRepository repository;
    private final Snowflake snowflake;

    public ConnectionManagerService(DatabaseConnectionRepository repository, Snowflake snowflake) {
        this.repository = repository;
        this.snowflake = snowflake;
    }

    public DatabaseConnection create(DatabaseConnection databaseConnection) {
        if (databaseConnection.getDatabase() == null || databaseConnection.getDatabase().isBlank()) {
            throw new IllegalArgumentException("Invalid database name");
        } else if (databaseConnection.getHost() == null || databaseConnection.getHost().isBlank()) {
            throw new IllegalArgumentException("Invalid host name");
        } else if (databaseConnection.getPassword() == null || databaseConnection.getPassword().isBlank()) {
            throw new IllegalArgumentException("Invalid password");
        } else if (databaseConnection.getPort() < 0 || databaseConnection.getPort() > 65_534) {
            throw new IllegalArgumentException("Port must be valid");
        } else if (databaseConnection.getDriver() == null) {
            throw new IllegalArgumentException("Invalid driver");
        }

        databaseConnection.setId(snowflake.next());
        return repository.save(databaseConnection);
    }

    public DatabaseConnection update(DatabaseConnection request) {
        if (repository.findById(request.getId()).isPresent()) {
            return repository.save(request);
        }

        throw new IllegalArgumentException("Database Connection not found");
    }

    public DatabaseConnection delete(long id) {
        DatabaseConnection conn = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Database Connection not found"));

        repository.delete(conn);

        return conn;
    }
}
