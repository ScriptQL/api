package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.dto.DatabaseConnectionDTO;
import com.scriptql.scriptqlapi.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.entities.DatabaseConnectionReviewer;
import com.scriptql.scriptqlapi.repositories.DatabaseConnectionRepository;
import com.scriptql.scriptqlapi.repositories.RoleRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@AllArgsConstructor
@Service
public class DatabaseConnectionService {

    private DatabaseConnectionRepository repository;
    private RoleRepository roleRepository;
    private Snowflake snowflake;

    @Transactional
    public DatabaseConnection create(DatabaseConnectionDTO databaseConnectionDTO) {
        var momentLocalDate = LocalDateTime.now();
        var databaseConnection = new DatabaseConnection();

        databaseConnection.setId(snowflake.next());
        databaseConnection.setName(databaseConnectionDTO.getName());
        databaseConnection.setDatabase(databaseConnectionDTO.getDatabase());
        databaseConnection.setUsername(databaseConnectionDTO.getUsername());
        databaseConnection.setHost(databaseConnectionDTO.getHost());
        databaseConnection.setPassword(databaseConnectionDTO.getPassword());
        databaseConnection.setPort(databaseConnectionDTO.getPort());
        databaseConnection.setDriver(databaseConnectionDTO.getDriver());
        databaseConnection.setCreatedAt(momentLocalDate);
        databaseConnection.setUpdatedAt(momentLocalDate);

        if (databaseConnectionDTO.getRoles() != null && !databaseConnectionDTO.getRoles().isEmpty()) {
            var reviewers = getDatabaseConnectionReviewers(
                    databaseConnection,
                    databaseConnectionDTO.getRoles()
            );
            databaseConnection.setDatabaseConnectionReviewers(reviewers);
        }

        repository.save(databaseConnection);

        return databaseConnection;
    }

    public ArrayList<DatabaseConnectionReviewer> getDatabaseConnectionReviewers(
            DatabaseConnection databaseConnection,
            Set<String> roles
    ) {
        var reviewers = new ArrayList<DatabaseConnectionReviewer>();

        roles
                .stream()
                .forEach(roleName -> {
                    var optRole = roleRepository.findRoleByName(roleName);

                    if (optRole.isPresent()) {
                        var reviewer = new DatabaseConnectionReviewer();
                        reviewer.setDatabaseConnection(databaseConnection);
                        reviewer.setRole(optRole.get());
                        reviewers.add(reviewer);
                    }
                });

        return reviewers;
    }
}
