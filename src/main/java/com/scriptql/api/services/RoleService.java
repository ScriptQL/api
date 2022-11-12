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
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final RoleRepository repository;
    private final UserRoleRepository userRoles;
    private final UserRepository userRepo;
    private final SpecBuilder<Role> builder = new SpecBuilder<>();

    public RoleService(RoleRepository repository, UserRoleRepository userRoles, UserRepository userRepo) {
        this.repository = repository;
        this.userRoles = userRoles;
        this.userRepo = userRepo;
        this.builder.addMatcher("name", SpecMatcher.SEARCH);
    }

    public @NotNull PagedResponse<Role> search(Role request, Paginator paginator) {
        Specification<Role> specification = this.builder.create(request, false);
        return new PagedResponse<>(this.repository.findAll(specification, paginator.toPageable()));
    }

    public @NotNull Role findById(long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new UserError("Unknown role"));
    }

    public @NotNull Role create(Role request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setCreatedAt(System.currentTimeMillis());
        role.setUpdatedAt(System.currentTimeMillis());
        return this.repository.save(role);
    }

    public void delete(long id) {
        Role role = findById(id);
        repository.delete(role);
    }

    public @NotNull PagedResponse<User> findUsers(long id, Paginator paginator) {
        Role role = this.findById(id);
        Page<UserRole> page = this.userRoles.findAllByRole(role, paginator.toPageable());
        PagedResponse<User> response = new PagedResponse<>();
        response.loadFrom(page, page.get()
                .map(UserRole::getUser)
                .toList());
        return response;
    }

    public UserRole createUserRole(long id, User request) {
        Role role = this.findById(id);
        User user = this.userRepo.findById(request.getId()).orElseThrow(() -> new UserError("Unknow user"));
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);

        return this.userRoles.save(userRole);
    }

    @Transactional
    public void deleteUserRole(long id, long userId) {
        Role role = this.findById(id);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new UserError("Unknow user"));

        this.userRoles.deleteUserRoleByRoleAndUser(role, user);
    }
}
