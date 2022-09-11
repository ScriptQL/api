package com.scriptql.scriptqlapi.repositories;

import com.scriptql.scriptqlapi.entities.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<Query, Long> {
}
