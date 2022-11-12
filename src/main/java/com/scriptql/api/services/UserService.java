package com.scriptql.api.services;

import com.scriptql.api.advice.security.Session;
import com.scriptql.api.domain.PagedResponse;
import com.scriptql.api.domain.Paginator;
import com.scriptql.api.domain.SpecBuilder;
import com.scriptql.api.domain.SpecMatcher;
import com.scriptql.api.domain.entities.Role;
import com.scriptql.api.domain.entities.User;
import com.scriptql.api.domain.entities.UserRole;
import com.scriptql.api.domain.enums.UserGroup;
import com.scriptql.api.domain.errors.SecurityError;
import com.scriptql.api.domain.errors.UserError;
import com.scriptql.api.domain.repositories.RoleRepository;
import com.scriptql.api.domain.repositories.UserRepository;
import com.scriptql.api.domain.repositories.UserRoleRepository;
import com.scriptql.api.domain.request.EditUserPassword;
import com.scriptql.api.domain.request.EditUserRequest;
import org.apache.catalina.Store;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRepository repository;
    private final RoleRepository roles;
    private final BCryptPasswordEncoder bcrypt;
    private final UserRoleRepository userRoles;
    private final SpecBuilder<User> builder = new SpecBuilder<>();
    private Store users;

    public UserService(
            UserRepository userRepository, UserRepository repository,
            RoleRepository roles,
            BCryptPasswordEncoder bcrypt, UserRoleRepository userRoles) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.roles = roles;
        this.bcrypt = bcrypt;
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

    public User edit(long id, EditUserRequest request) throws SecurityError {
        User user = this.findById(id);

        if (!Session.getUser().getAccess().equals(UserGroup.ADMIN)) {
            throw new SecurityError(403, "Unauthorized");
        }

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }

        return this.repository.save(user);
    }

    public @NotNull List<Role> getRoles(long id) {
        User user = this.findById(id);
        return this.userRoles.findAllByUser(user)
                .stream().map(UserRole::getRole)
                .toList();
    }

    public User newPassword(EditUserPassword request) throws SecurityError {
        User user = Session.getUser();

        if (!request.getNewPwd().equals(request.getNewPwdConfirmation())) {
            throw new SecurityError(403, "New password field don't match");
        } else {
           user.setPassword(this.bcrypt.encode(request.getNewPwd()));
           this.userRepository.save(user);
        }
        return user;
    }

}
