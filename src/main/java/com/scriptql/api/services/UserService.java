package com.scriptql.api.services;

import com.scriptql.api.domain.PagedResponse;
import com.scriptql.api.domain.Paginator;
import com.scriptql.api.domain.SpecBuilder;
import com.scriptql.api.domain.SpecMatcher;
import com.scriptql.api.domain.entities.Role;
import com.scriptql.api.domain.entities.User;
import com.scriptql.api.domain.entities.UserRole;
import com.scriptql.api.domain.errors.UserError;
import com.scriptql.api.domain.repositories.RoleRepository;
import com.scriptql.api.domain.repositories.UserRepository;
import com.scriptql.api.domain.repositories.UserRoleRepository;
import com.scriptql.api.domain.request.EditUserRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roles;
    private final UserRoleRepository userRoles;
    private final SpecBuilder<User> builder = new SpecBuilder<>();

    public UserService(
            UserRepository repository,
            RoleRepository roles,
            UserRoleRepository userRoles) {
        this.repository = repository;
        this.roles = roles;
        this.userRoles = userRoles;
        this.builder.addMatcher("name", SpecMatcher.SEARCH);
    }

    public @NotNull PagedResponse<User> search(User request, Paginator paginator) {
        Specification<User> specification = this.builder.create(request, false);
        return new PagedResponse<>(this.repository.findAll(specification, paginator.toPageable()));
    }

    public @NotNull User findById(long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new UserError("Unknown user"));
    }

    public @NotNull User create(User request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());
        return this.repository.save(user);
    }

    public User edit(long id, EditUserRequest request) {
        User user = this.findById(id);

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getRoles() != null) {
            this.userRoles.deleteAllByUser(user);
            List<UserRole> roles = this.roles.findAllById(request.getRoles()).stream().map(role -> {
                UserRole mapping = new UserRole();
                mapping.setRole(role);
                mapping.setUser(user);
                return mapping;
            }).toList();
            if (!roles.isEmpty()) {
                this.userRoles.saveAll(roles);
            }
        }

        return user;
    }

    public @NotNull List<Role> getRoles(long id) {
        User user = this.findById(id);
        return this.userRoles.findAllByUser(user)
                .stream().map(UserRole::getRole)
                .toList();
    }

}
