package com.scriptql.api.services;

import com.scriptql.api.domain.PagedResponse;
import com.scriptql.api.domain.Paginator;
import com.scriptql.api.domain.SpecBuilder;
import com.scriptql.api.domain.SpecMatcher;
import com.scriptql.api.domain.entities.Review;
import com.scriptql.api.domain.errors.UserError;
import com.scriptql.api.domain.repositories.ReviewRepository;
import com.scriptql.api.domain.request.EditReviewRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository repository;
    private final SpecBuilder<Review> builder = new SpecBuilder<>();

    public ReviewService(ReviewRepository repository) {
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

    public @NotNull Review edit(long id, EditReviewRequest request) {
        Review review = this.findById(id);

        if (review.getAccepted() != null) {
            throw new UserError("This review is already concluded.");
        }

        review.setDateReviewed(System.currentTimeMillis());
        review.setComment(request.getComment());
        review.setAccepted(request.isAccepted());

        return this.repository.save(review);
    }

}
