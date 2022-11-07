package com.scriptql.api.services;

import com.scriptql.api.advice.security.Session;
import com.scriptql.api.domain.PagedResponse;
import com.scriptql.api.domain.Paginator;
import com.scriptql.api.domain.SpecBuilder;
import com.scriptql.api.domain.SpecMatcher;
import com.scriptql.api.domain.entities.*;
import com.scriptql.api.domain.enums.QueryStatus;
import com.scriptql.api.domain.errors.UserError;
import com.scriptql.api.domain.repositories.QueryRepository;
import com.scriptql.api.domain.repositories.ReviewRepository;
import com.scriptql.api.domain.repositories.RoleRepository;
import com.scriptql.api.domain.repositories.UserRoleRepository;
import com.scriptql.api.domain.request.CreateQueryRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QueryService {

    private final QueryRepository repository;
    private final ConnectionService connections;
    private final UserRoleRepository userRoles;
    private final RoleRepository roles;
    private final ReviewRepository reviews;
    private final NotificationService notification;

    private final SpecBuilder<Query> builder = new SpecBuilder<>();

    public QueryService(
            QueryRepository repository,
            ConnectionService connections,
            UserRoleRepository userRoles,
            RoleRepository roles, ReviewRepository reviews,
            NotificationService notification) {
        this.repository = repository;
        this.connections = connections;
        this.userRoles = userRoles;
        this.roles = roles;
        this.reviews = reviews;
        this.notification = notification;
        this.builder.addMatcher("database.name", SpecMatcher.SEARCH);
        this.builder.addMatcher("description", SpecMatcher.SEARCH);
    }

    public @NotNull PagedResponse<Query> search(Query request, Paginator paginator) {
        Specification<Query> specification = this.builder.create(request, false);
        return new PagedResponse<>(this.repository.findAll(specification, paginator.toPageable()));
    }

    public @NotNull Query findById(long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new UserError("Unknown query"));
    }

    public @NotNull Query create(CreateQueryRequest request) {
        DatabaseConnection connection = this.connections.findById(request.getConnection());

        Query query = new Query();
        query.setTitle(request.getTitle());
        query.setQuery(request.getQuery());
        query.setDescription(request.getDescription());
        query.setConnection(connection);
        query.setRequester(Session.getUser());
        query.setStatus(QueryStatus.WAITING_REVIEW);

        List<Review> reviews = new ArrayList<>();
        for (Long id : request.getReviewers()) {
            Role role = this.roles.findById(id)
                    .orElseThrow(() -> new UserError("Invalid role"));
            List<User> users = this.userRoles.findAllByRole(role).stream()
                    .map(UserRole::getUser)
                    .toList();
            if (users.isEmpty()) {
                throw new UserError("No users for review under " + role.getName());
            }
            Review review = new Review();
            review.setUser(users.get(new Random().nextInt(users.size())));
            review.setQuery(query);
            reviews.add(review);
        }

        query = this.repository.save(query);
        this.reviews.saveAll(reviews);
        notification.sendMessage(query);
        return query;
    }

    public @NotNull List<Review> getReviews(long id) {
        Query query = this.findById(id);
        return this.reviews.findAllByQuery(query);
    }

}
