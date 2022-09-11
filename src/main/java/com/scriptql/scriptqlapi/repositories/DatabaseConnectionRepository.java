package com.scriptql.scriptqlapi.repositories;

import com.scriptql.scriptqlapi.entities.DatabaseConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseConnectionRepository extends JpaRepository<DatabaseConnection, Long> {
}
