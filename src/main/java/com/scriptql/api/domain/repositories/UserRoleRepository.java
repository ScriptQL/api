package com.scriptql.api.domain.repositories;

import com.scriptql.api.domain.entities.Role;
import com.scriptql.api.domain.entities.User;
import com.scriptql.api.domain.entities.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {

    List<UserRole> findAllByRole(Role role);

    Page<UserRole> findAllByRole(Role role, Pageable pageable);

    List<UserRole> findAllByUser(User user);

    void deleteAllByUser(User user);

    void deleteUserRoleByRoleAndUser(Role role, User user);

}
