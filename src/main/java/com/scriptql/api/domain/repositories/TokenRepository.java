package com.scriptql.api.domain.repositories;

import com.scriptql.api.domain.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByCode(String code);

}
