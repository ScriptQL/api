package com.scriptql.scriptqlapi.domain.repositories;

import com.scriptql.scriptqlapi.domain.entities.DatabaseConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseConnectionRepository extends JpaRepository<DatabaseConnection, Long> {
}
