package com.scriptql.scriptqlapi.domain.repositories;

import com.scriptql.scriptqlapi.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(String name);

    List<Role> findRolesByNameIn(List<String> names);
}
