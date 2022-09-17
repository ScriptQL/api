package com.scriptql.scriptqlapi.rest.mappers.response;

import com.scriptql.scriptqlapi.domain.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.domain.entities.DatabaseConnectionReviewer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DatabaseConnectionResponseMapper {

    private long id;
    private String name;
    private String host;
    private String database;
    private String username;
    private int port;
    private String driver;
    private List<RoleResponseMapper> reviewers = new ArrayList<>();

    public static DatabaseConnectionResponseMapper fromDatabaseConnection(DatabaseConnection databaseConnection) {
        var mapper = new DatabaseConnectionResponseMapper();

        mapper.setId(databaseConnection.getId());
        mapper.setName(databaseConnection.getName());
        mapper.setHost(databaseConnection.getHost());
        mapper.setDatabase(databaseConnection.getDatabase());
        mapper.setUsername(databaseConnection.getUsername());
        mapper.setPort(databaseConnection.getPort());
        mapper.setDriver(databaseConnection.getDriver().toString());

        if (
            databaseConnection.getDatabaseConnectionReviewers() != null &&
            !databaseConnection.getDatabaseConnectionReviewers().isEmpty()
        ) {
            for (var databaseConnectionReviewer : databaseConnection.getDatabaseConnectionReviewers()) {
                mapper.getReviewers().add(RoleResponseMapper.fromRole(databaseConnectionReviewer.getRole()));
            }
        }

        return mapper;
    }
}
