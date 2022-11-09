package com.scriptql.api.domain.repositories;

import com.scriptql.api.domain.entities.DatabaseConnection;
import com.scriptql.api.domain.entities.Reviewer;
import com.scriptql.api.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DatabaseReviewerRepository extends JpaRepository<Reviewer, Long> {
    List<Reviewer> findDatabaseConnectionReviewerByDatabaseConnectionId(long id);

    void deleteByDatabaseConnection(DatabaseConnection connection);

    List<Reviewer> findAllByDatabaseConnection(DatabaseConnection connection);

    Optional<Reviewer> findByRoleAndDatabaseConnection(Role role, DatabaseConnection connection);
}
