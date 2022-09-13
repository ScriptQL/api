package com.scriptql.scriptqlapi.repositories;

import com.scriptql.scriptqlapi.entities.DatabaseConnectionReviewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatabaseConnectionReviewerRepository extends JpaRepository<DatabaseConnectionReviewer, Long> {
    List<DatabaseConnectionReviewer> findDatabaseConnectionReviewerByDatabaseConnectionId(long id);
}
