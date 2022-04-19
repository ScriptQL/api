package com.scriptql.scriptqlapi.controllers;

import com.scriptql.scriptqlapi.services.AbstractService;
import com.scriptql.scriptqlapi.entities.IEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public abstract class AbstractController<T extends IEntity> {

    private final AbstractService<T> service;

    @GetMapping()
    public List<T> list() {
        return service.findAll();
    }

    @GetMapping("/{id:[0-9]+}")
    public T list(@PathVariable("id") long id) {
        return service.findOne(id);
    }

    @PostMapping()
    public T create(@Valid @RequestBody T request) {
        return service.create(request);
    }

    @PatchMapping()
    public T update(@Valid @RequestBody T request) {
        return service.update(request);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public void delete(@Valid @PathVariable("id") long id) {
        service.delete(id);
    }

}
