package com.scriptql.scriptqlapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractRepository<T> extends JpaRepository<T, Long> {
}
