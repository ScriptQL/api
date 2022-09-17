package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.domain.entities.Role;
import com.scriptql.scriptqlapi.rest.exceptions.RoleNotFoundException;
import com.scriptql.scriptqlapi.domain.repositories.RoleRepository;
import com.scriptql.scriptqlapi.rest.mappers.request.RoleRequestMapper;
import com.scriptql.scriptqlapi.utils.Snowflake;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
public class RoleService {

    private RoleRepository repository;
    private Snowflake snowflake;

    public Role create(RoleRequestMapper mapper) {
        var role = new Role();
        var instant = Instant.now().getEpochSecond();

        role.setId(snowflake.next());
        role.setName(mapper.getName());
        role.setCreatedAt(instant);
        role.setUpdatedAt(instant);

        return repository.save(role);
    }

    public List<Role> findAll() {
        return this.repository.findAll();
    }

    public Role findById(long id) {
        return this.repository.findById(id).orElseThrow(RoleNotFoundException::new);
    }

    public void delete(long id) {
        Role role = findById(id);
        repository.delete(role);
    }
}
