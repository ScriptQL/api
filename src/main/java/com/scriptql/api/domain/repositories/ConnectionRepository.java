package com.scriptql.api.domain.repositories;

import com.scriptql.api.domain.entities.DatabaseConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConnectionRepository extends
        JpaRepository<DatabaseConnection, Long>,
        JpaSpecificationExecutor<DatabaseConnection> {
}
