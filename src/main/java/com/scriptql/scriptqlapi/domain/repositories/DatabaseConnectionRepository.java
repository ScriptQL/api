package com.scriptql.scriptqlapi.domain.repositories;

import com.scriptql.scriptqlapi.domain.entities.DatabaseConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DatabaseConnectionRepository extends JpaRepository<DatabaseConnection, Long> {

    @Query("SELECT dc FROM DatabaseConnection dc " +
            "LEFT JOIN FETCH dc.databaseConnectionReviewers dcr " +
            "LEFT JOIN FETCH dcr.role " +
            "WHERE dc.id = (:id)")
    Optional<DatabaseConnection> findByIdAndFetchDatabaseConnectionReviewersEagerly(@Param("id") long id);
}
