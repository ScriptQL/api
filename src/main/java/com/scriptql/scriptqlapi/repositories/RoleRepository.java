package com.scriptql.scriptqlapi.repositories;

import com.scriptql.scriptqlapi.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Optional<Role> findRoleByName(String name);
}
