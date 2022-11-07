package com.scriptql.api.services;

import com.scriptql.api.advice.security.Session;
import com.scriptql.api.domain.PagedResponse;
import com.scriptql.api.domain.Paginator;
import com.scriptql.api.domain.SpecBuilder;
import com.scriptql.api.domain.SpecMatcher;
import com.scriptql.api.domain.entities.Query;
import com.scriptql.api.domain.entities.Review;
import com.scriptql.api.domain.enums.QueryStatus;
import com.scriptql.api.domain.errors.UserError;
import com.scriptql.api.domain.repositories.QueryRepository;
import com.scriptql.api.domain.repositories.ReviewRepository;
import com.scriptql.api.domain.request.EditReviewRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final QueryRepository queries;
    private final ReviewRepository repository;
    private final SpecBuilder<Review> builder = new SpecBuilder<>();

    public ReviewService(QueryRepository queries, ReviewRepository repository) {
        this.queries = queries;
        this.repository = repository;
        this.builder.addMatcher("comment", SpecMatcher.SEARCH);
    }

    public @NotNull PagedResponse<Review> search(Review request, Paginator paginator) {
        Specification<Review> specification = this.builder.create(request, false);
        return new PagedResponse<>(this.repository.findAll(specification, paginator.toPageable()));
    }

    public @NotNull Review findById(long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new UserError("Unknown review"));
    }

    public @NotNull Review create(EditReviewRequest request) {
        Query query = this.queries.findById(request.getQuery())
                .filter(q -> q.getStatus() == QueryStatus.WAITING_REVIEW)
                .orElseThrow(() -> new UserError("Invalid query"));
        Review review = this.repository.findByUserAndQuery(Session.getUser(), query)
                .orElse(new Review());
        if (review.getAccepted() != null) {
            throw new UserError("This review is already concluded.");
        }
        review.setQuery(query);
        review.setUser(Session.getUser());
        review.setComment(request.getComment());
        review.setAccepted(request.isAccepted());
        review.setDateReviewed(System.currentTimeMillis());
        review = this.repository.save(review);
        if (repository.countAllByQueryAndAccepted(query, false) > 0) {
            query.setStatus(QueryStatus.REJECTED);
            this.queries.save(query);
        } else if (repository.countAllByQueryAndAccepted(query, null) == 0) {
            query.setStatus(QueryStatus.APPROVED);
            this.queries.save(query);
        }
        return review;
    }

}
