package com.scriptql.api.services;

import com.scriptql.api.domain.PagedResponse;
import com.scriptql.api.domain.Paginator;
import com.scriptql.api.domain.SpecBuilder;
import com.scriptql.api.domain.SpecMatcher;
import com.scriptql.api.domain.entities.DatabaseConnection;
import com.scriptql.api.domain.entities.Reviewer;
import com.scriptql.api.domain.entities.Role;
import com.scriptql.api.domain.errors.UserError;
import com.scriptql.api.domain.repositories.ConnectionRepository;
import com.scriptql.api.domain.repositories.DatabaseReviewerRepository;
import com.scriptql.api.domain.repositories.RoleRepository;
import com.scriptql.api.domain.request.CreateConnectionRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConnectionService {

    private final ConnectionRepository repository;
    private final DatabaseReviewerRepository reviewers;
    private final RoleRepository roles;

    private final SpecBuilder<DatabaseConnection> builder = new SpecBuilder<>();

    public ConnectionService(
            ConnectionRepository repository,
            DatabaseReviewerRepository reviewers,
            RoleRepository roles) {
        this.repository = repository;
        this.reviewers = reviewers;
        this.roles = roles;
        this.builder.addMatcher("name", SpecMatcher.SEARCH);
        this.builder.addMatcher("host", SpecMatcher.SEARCH);
    }

    public @NotNull PagedResponse<DatabaseConnection> search(DatabaseConnection request, Paginator paginator) {
        Specification<DatabaseConnection> specification = this.builder.create(request, false);
        return new PagedResponse<>(this.repository.findAll(specification, paginator.toPageable()));
    }

    public @NotNull DatabaseConnection findById(long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new UserError("Unknown connection"));
    }

    @Transactional
    public @NotNull DatabaseConnection create(CreateConnectionRequest request) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.setName(request.getName());
        connection.setHost(request.getHost());
        connection.setPort(request.getPort());
        connection.setDriver(request.getDriver());
        connection.setDatabase(request.getDatabase());
        connection.setUsername(request.getUsername());
        connection.setPassword(request.getPassword());
        connection.setCreatedAt(System.currentTimeMillis());
        connection.setUpdatedAt(System.currentTimeMillis());

        return this.repository.save(connection);
    }

    @Transactional
    public DatabaseConnection edit(long id, CreateConnectionRequest request) {
        DatabaseConnection connection = this.findById(id);

        if (request.getHost() != null) {
            connection.setHost(request.getHost());
        }
        if (request.getPort() != null) {
            connection.setPort(request.getPort());
        }
        if (request.getUsername() != null) {
            connection.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            connection.setPassword(request.getPassword());
        }
        if (request.getDriver() != null) {
            connection.setDriver(request.getDriver());
        }
        if (request.getDatabase() != null) {
            connection.setDatabase(request.getDatabase());
        }

        return this.repository.save(connection);
    }

    public @NotNull List<Role> getReviewers(long id) {
        DatabaseConnection connection = this.findById(id);
        return this.reviewers.findAllByDatabaseConnection(connection)
                .stream().map(Reviewer::getRole)
                .toList();
    }

    public @NotNull Reviewer addReviewer(long id, Role request) {
        DatabaseConnection connection = this.findById(id);
        Role role = this.roles.findById(request.getId())
                .orElseThrow(() -> new UserError("Invalid role"));
        if (this.reviewers.findByRoleAndDatabaseConnection(role, connection)
                .isPresent()) {
            throw new UserError("This role is already registered as a reviewer");
        }
        Reviewer reviewer = new Reviewer();
        reviewer.setDatabaseConnection(connection);
        reviewer.setRole(role);
        return this.reviewers.save(reviewer);
    }

    public void delReviewer(long id, long roleId) {
        DatabaseConnection connection = this.findById(id);
        Role role = this.roles.findById(roleId)
                .orElseThrow(() -> new UserError("Invalid role"));
        Reviewer reviewer = this.reviewers.findByRoleAndDatabaseConnection(role, connection)
                .orElseThrow(() -> new UserError("This role is not a reviewer"));
        this.reviewers.delete(reviewer);
    }

}
