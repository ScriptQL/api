package com.scriptql.scriptqlapi.repositories;

import com.scriptql.scriptqlapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
}
