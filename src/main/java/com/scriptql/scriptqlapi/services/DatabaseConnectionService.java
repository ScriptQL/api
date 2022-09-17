package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.rest.mappers.request.DatabaseConnectionRequestMapper;
import com.scriptql.scriptqlapi.domain.entities.DatabaseConnection;
import com.scriptql.scriptqlapi.domain.entities.DatabaseConnectionReviewer;
import com.scriptql.scriptqlapi.domain.entities.Role;
import com.scriptql.scriptqlapi.rest.exceptions.DatabaseConnectionNotFoundException;
import com.scriptql.scriptqlapi.domain.repositories.DatabaseConnectionRepository;
import com.scriptql.scriptqlapi.domain.repositories.DatabaseConnectionReviewerRepository;
import com.scriptql.scriptqlapi.domain.repositories.RoleRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class DatabaseConnectionService {

    private DatabaseConnectionRepository repository;
    private DatabaseConnectionReviewerRepository databaseConnectionReviewerRepository;
    private RoleRepository roleRepository;
    private Snowflake snowflake;

    @Transactional
    public DatabaseConnection create(DatabaseConnectionRequestMapper databaseConnectionRequestMapper) {
        var instant = Instant.now().getEpochSecond();
        var databaseConnection = databaseConnectionRequestMapper.buildDatabaseConnection();

        databaseConnection.setId(snowflake.next());
        databaseConnection.setCreatedAt(instant);
        databaseConnection.setUpdatedAt(instant);

        var reviewers = getDatabaseConnectionReviewers(
                databaseConnection,
                databaseConnectionRequestMapper.getRoles()
        );

        repository.save(databaseConnection);
        databaseConnectionReviewerRepository.saveAll(reviewers);
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
                        reviewers.add(
                                buildReviewerFromRoleAndConnection(databaseConnection, optRole.get())
                        );
                    }
                });

        return reviewers;
    }

    public DatabaseConnectionReviewer buildReviewerFromRoleAndConnection(DatabaseConnection db, Role role) {
        var reviewer = new DatabaseConnectionReviewer();
        reviewer.setId(snowflake.next());
        reviewer.setDatabaseConnection(db);
        reviewer.setRole(role);
        return reviewer;
    }

    public List<DatabaseConnection> findAll() {
        return repository.findAll();
    }

    public DatabaseConnection findById(long id) {
        return repository
                .findByIdAndFetchDatabaseConnectionReviewersEagerly(id)
                .orElseThrow(DatabaseConnectionNotFoundException::new);
    }

    @Transactional
    public void update(long id, DatabaseConnectionRequestMapper databaseConnectionPayload) {
        var databaseConnection = findById(id);
        var newDatabaseConnection = databaseConnectionPayload.buildDatabaseConnection();
        newDatabaseConnection.setId(databaseConnection.getId());
        newDatabaseConnection.setUpdatedAt(Instant.now().getEpochSecond());
        newDatabaseConnection.setCreatedAt(databaseConnection.getCreatedAt());
        newDatabaseConnection.setDatabaseConnectionReviewers(Collections.emptyList());

        var newReviewers = new ArrayList<DatabaseConnectionReviewer>();
        roleRepository
                .findRolesByNameIn(databaseConnectionPayload.getRoles().stream().toList())
                .forEach(role -> {
                    newReviewers.add(
                            buildReviewerFromRoleAndConnection(newDatabaseConnection, role)
                    );
                });

        newDatabaseConnection.setDatabaseConnectionReviewers(newReviewers);
        repository.save(newDatabaseConnection);
        databaseConnectionReviewerRepository.saveAll(newReviewers);
    }

    public void delete(long id) {
        var databaseConnection = repository
                .findById(id)
                .orElseThrow(DatabaseConnectionNotFoundException::new);

        repository.delete(databaseConnection);
    }
}
