package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.entities.IEntity;
import com.scriptql.scriptqlapi.repositories.AbstractRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class AbstractService<T extends IEntity> {

    protected final AbstractRepository<T> repository;
    protected final Snowflake snowflake;

    public T create(T entity) {
        entity.setId(snowflake.next());
        this.validate(entity);
        return repository.save(entity);
    }

    public T update(T entity) {
        if (repository.existsById(entity.getId())) {
            throw new IllegalArgumentException("Database Connection not found");
        }
        this.validate(entity);
        return repository.save(entity);
    }

    public T findOne(long id) {
        return this.repository.findById(id).orElseThrow();
    }

    public List<T> findAll() {
        return this.repository.findAll();
    }

    public void delete(long id) {
        T entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity not found"));

        repository.delete(entity);
    }

    protected void validate(T entity) {
    }

}
