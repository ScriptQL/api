package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.domain.entities.QueryReview;
import com.scriptql.scriptqlapi.domain.repositories.*;
import com.scriptql.scriptqlapi.rest.mappers.QueryMapper;
import com.scriptql.scriptqlapi.domain.entities.Query;
import com.scriptql.scriptqlapi.domain.enums.QueryStatus;
import com.scriptql.scriptqlapi.rest.exceptions.DatabaseConnectionNotFoundException;
import com.scriptql.scriptqlapi.rest.exceptions.UserNotFoundException;
import com.scriptql.scriptqlapi.utils.Snowflake;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class QueryService {

    private QueryRepository repository;
    private QueryReviewRepository queryReviewRepository;
    private DatabaseConnectionRepository databaseConnectionRepository;
    private DatabaseConnectionReviewerRepository databaseConnectionReviewerRepository;
    private UserRepository userRepository;
    private Snowflake snowflake;

    public Query create(QueryMapper mapper) {
        var databaseConnection = databaseConnectionRepository
                .findById(mapper.getDatabaseConnectionId())
                .orElseThrow(DatabaseConnectionNotFoundException::new);
        var user = userRepository
                .findById(mapper.getUserId())
                .orElseThrow(UserNotFoundException::new);
        var databaseConnectionReviewers = databaseConnectionReviewerRepository
                .findDatabaseConnectionReviewerByDatabaseConnectionId(databaseConnection.getId());
        var query = new Query();
        var instant = Instant.now().getEpochSecond();

        query.setId(snowflake.next());
        query.setQuery(mapper.getQuery());
        query.setDescription(mapper.getDescription());
        query.setStatus(QueryStatus.WAITING_REVIEW);
        query.setDatabase(databaseConnection);
        query.setUser(user);
        query.setCreatedAt(instant);
        query.setUpdatedAt(instant);

        var queryReviewers = new ArrayList<QueryReview>();

        if (!databaseConnectionReviewers.isEmpty()) {
            databaseConnectionReviewers.forEach(databaseConnectionReviewer -> {
                var queryReview = new QueryReview();

                queryReview.setId(snowflake.next());
                queryReview.setQuery(query);
                queryReview.setRoleName(databaseConnectionReviewer.getRole().getName());

                queryReviewers.add(queryReview);
            });
        }

        repository.save(query);
        queryReviewRepository.saveAll(queryReviewers);
        query.setQueryReviews(queryReviewers);

        return query;
    }
}
