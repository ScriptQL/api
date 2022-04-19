package com.scriptql.scriptqlapi.repositories;

import com.scriptql.scriptqlapi.entities.User;

import java.util.Optional;

public interface UserRepository extends AbstractRepository<User> {

    Optional<User> findFirstByLoginAndPassword(String login, String password);

}
