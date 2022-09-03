package com.scriptql.scriptqlapi.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractRepository<T> extends JpaRepository<T, Long> {
}
