package com.scriptql.api.domain.repositories;

import com.scriptql.api.domain.entities.DatabaseConnection;
import com.scriptql.api.domain.entities.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatabaseReviewerRepository extends JpaRepository<Reviewer, Long> {
    List<Reviewer> findDatabaseConnectionReviewerByDatabaseConnectionId(long id);

    void deleteByDatabaseConnection(DatabaseConnection connection);

    List<Reviewer> findAllByDatabaseConnection(DatabaseConnection connection);

}
