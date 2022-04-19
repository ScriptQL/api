package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.repositories.QueryRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import com.scriptql.scriptqlapi.utils.entities.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public void delete(long id) {
        Query query = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Query not found"));
        if (Objects.nonNull(query.getExecutionDate())) {
            throw new IllegalArgumentException("NAO PODE DELETAR QUERY QUE JA FOI EXECUTADA");
        }

        repository.delete(query);
    }

    public Query execute(Query query) {
        query = repository.findById(query.getId()).orElseThrow(() -> new IllegalArgumentException("Query not found"));
        query.setExecutionDate(LocalDateTime.now());

        //executa a query

        return repository.save(query);
    }

}
