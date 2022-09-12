package com.scriptql.scriptqlapi.repositories;

import com.scriptql.scriptqlapi.entities.DatabaseConnectionReviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DatabaseConnectionReviewerRepository extends JpaRepository<DatabaseConnectionReviewer, Long> {
    List<DatabaseConnectionReviewer> findDatabaseConnectionReviewerByDatabaseConnectionId(long id);

    void deleteDatabaseConnectionReviewersByRoleNameNotIn(String[] roles);
}
