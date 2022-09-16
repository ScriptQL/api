package com.scriptql.scriptqlapi.domain.repositories;

import com.scriptql.scriptqlapi.domain.entities.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<Query, Long> {
}
