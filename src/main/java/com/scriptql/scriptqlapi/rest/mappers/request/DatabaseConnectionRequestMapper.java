package com.scriptql.scriptqlapi.rest.mappers.request;

import com.scriptql.scriptqlapi.domain.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.domain.enums.DatabaseDriver;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
public class DatabaseConnectionRequestMapper {

    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String host;
    @NotEmpty
    private String database;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private int port;
    private DatabaseDriver driver;
    private Set<String> roles = new HashSet<>();

    public DatabaseConnection buildDatabaseConnection() {
        var databaseConnection = new DatabaseConnection();

        databaseConnection.setName(getName());
        databaseConnection.setDatabase(getDatabase());
        databaseConnection.setUsername(getUsername());
        databaseConnection.setHost(getHost());
        databaseConnection.setPassword(getPassword());
        databaseConnection.setPort(getPort());
        databaseConnection.setDriver(getDriver());

        return databaseConnection;
    }
}
