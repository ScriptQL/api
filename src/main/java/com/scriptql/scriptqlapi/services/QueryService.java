package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.entities.Query;
import com.scriptql.scriptqlapi.abstractions.AbstractService;
import com.scriptql.scriptqlapi.repositories.QueryRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class QueryService extends AbstractService<Query> {

    public QueryService(QueryRepository repository, Snowflake snowflake) {
        super(repository, snowflake);
    }

    @Override
    public void validate(Query query) {
        if (Objects.nonNull(query.getExecutionDate())) {
            throw new IllegalArgumentException("Nao se pode manipular Querys ja executadas");
        }
    }

}
