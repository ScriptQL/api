package com.scriptql.api.rest.controllers;

import com.scriptql.api.domain.PagedResponse;
import com.scriptql.api.domain.Paginator;
import com.scriptql.api.domain.entities.Query;
import com.scriptql.api.domain.entities.Review;
import com.scriptql.api.domain.request.CreateQueryRequest;
import com.scriptql.api.services.QueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("queries")
public class QueryController {

    private final QueryService service;

    public QueryController(QueryService service) {
        this.service = service;
    }

    @GetMapping
    public PagedResponse<Query> search(Query request, Paginator paginator) {
        return this.service.search(request, paginator);
    }

    @GetMapping("/{id:\\d+}")
    public Query findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Query create(@RequestBody @Valid CreateQueryRequest request) {
        return this.service.create(request);
    }

    @GetMapping("/{id:\\d+}/reviews")
    public List<Review> getReviews(@PathVariable("id") long id) {
        return this.service.getReviews(id);
    }

    @PostMapping("/{id:\\d+}/execute")
    public void execute(@PathVariable("id") long id) {
        this.service.execute(id);
    }

    @GetMapping("/{id:\\d+}/download")
    public ResponseEntity<byte[]> download(@PathVariable("id") long id) {
        return this.service.download(id);
    }

}
