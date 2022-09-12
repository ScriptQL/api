package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.dto.DatabaseConnectionDTO;
import com.scriptql.scriptqlapi.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.entities.DatabaseConnectionReviewer;
import com.scriptql.scriptqlapi.exceptions.DatabaseConnectionNotFoundException;
import com.scriptql.scriptqlapi.repositories.DatabaseConnectionRepository;
import com.scriptql.scriptqlapi.repositories.DatabaseConnectionReviewerRepository;
import com.scriptql.scriptqlapi.repositories.RoleRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class DatabaseConnectionService {

    private DatabaseConnectionRepository repository;
    private DatabaseConnectionReviewerRepository datababaseConnectionReviewerRepository;
    private RoleRepository roleRepository;
    private Snowflake snowflake;

    @Transactional
    public DatabaseConnection create(DatabaseConnectionDTO databaseConnectionDTO) {
        var momentLocalDate = LocalDateTime.now();
        var databaseConnection = buildDatabaseConnectionFromDto(databaseConnectionDTO);

        databaseConnection.setId(snowflake.next());
        databaseConnection.setCreatedAt(momentLocalDate);
        databaseConnection.setUpdatedAt(momentLocalDate);

        var reviewers = getDatabaseConnectionReviewers(
                databaseConnection,
                databaseConnectionDTO.getRoles()
        );

        repository.save(databaseConnection);
        datababaseConnectionReviewerRepository.saveAll(reviewers);
        databaseConnection.setDatabaseConnectionReviewers(reviewers);

        return databaseConnection;
    }

    private ArrayList<DatabaseConnectionReviewer> getDatabaseConnectionReviewers(
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
                        reviewer.setId(snowflake.next());
                        reviewer.setDatabaseConnection(databaseConnection);
                        reviewer.setRole(optRole.get());
                        reviewers.add(reviewer);
                    }
                });

        return reviewers;
    }

    public List<DatabaseConnection> findAll() {
        return repository.findAll();
    }

    public DatabaseConnection findById(long id) {
        return repository
                .findById(id)
                .map(databaseConnection -> {
                    var reviewers = datababaseConnectionReviewerRepository.
                            findDatabaseConnectionReviewerByDatabaseConnectionId(databaseConnection.getId());

                    databaseConnection.setDatabaseConnectionReviewers(reviewers);
                    return databaseConnection;
                })
                .orElseThrow(DatabaseConnectionNotFoundException::new);
    }

    @Transactional
    public void update(long id, DatabaseConnectionDTO databaseConnectionPayload) {
        repository
                .findById(id)
                .map(databaseConnection -> {
                    var newDatabaseConnection = buildDatabaseConnectionFromDto(databaseConnectionPayload);
                    newDatabaseConnection.setId(databaseConnection.getId());
                    newDatabaseConnection.setUpdatedAt(LocalDateTime.now());
                    newDatabaseConnection.setCreatedAt(databaseConnection.getCreatedAt());
                    repository.save(newDatabaseConnection);

                    // @TODO: also update roles

                    return databaseConnection;
                })
                .orElseThrow(DatabaseConnectionNotFoundException::new);
    }

    public void delete(long id) {
        var databaseConnection = findById(id);
        repository.delete(databaseConnection);
    }

    private DatabaseConnection buildDatabaseConnectionFromDto(DatabaseConnectionDTO databaseConnectionDTO) {
        var databaseConnection = new DatabaseConnection();

        databaseConnection.setName(databaseConnectionDTO.getName());
        databaseConnection.setDatabase(databaseConnectionDTO.getDatabase());
        databaseConnection.setUsername(databaseConnectionDTO.getUsername());
        databaseConnection.setHost(databaseConnectionDTO.getHost());
        databaseConnection.setPassword(databaseConnectionDTO.getPassword());
        databaseConnection.setPort(databaseConnectionDTO.getPort());
        databaseConnection.setDriver(databaseConnectionDTO.getDriver());

        return databaseConnection;
    }
}
