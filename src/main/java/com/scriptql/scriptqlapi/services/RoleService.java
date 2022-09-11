package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.entities.Role;
import com.scriptql.scriptqlapi.exceptions.role.RoleNotFoundException;
import com.scriptql.scriptqlapi.repositories.RoleRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class RoleService {

    private RoleRepository repository;
    private Snowflake snowflake;

    public Role create(Role role) {
        var localDateTimeNow = LocalDateTime.now();

        role.setId(snowflake.next());
        role.setCreatedAt(localDateTimeNow);
        role.setUpdatedAt(localDateTimeNow);

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
