package com.scriptql.api.domain.repositories;

import com.scriptql.api.domain.entities.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QueryRepository extends JpaRepository<Query, Long>, JpaSpecificationExecutor<Query> {
}
