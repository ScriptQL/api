package com.scriptql.api.rest.controllers;

import com.scriptql.api.domain.PagedResponse;
import com.scriptql.api.domain.Paginator;
import com.scriptql.api.domain.entities.Review;
import com.scriptql.api.domain.request.EditReviewRequest;
import com.scriptql.api.services.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping
    public PagedResponse<Review> search(Review request, Paginator paginator) {
        return this.service.search(request, paginator);
    }

    @GetMapping("/{id:\\d+}")
    public Review findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @PostMapping
    public Review create(@RequestBody EditReviewRequest request) {
        return this.service.create(request);
    }

}
