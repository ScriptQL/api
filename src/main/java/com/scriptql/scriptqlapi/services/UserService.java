package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.domain.entities.User;
import com.scriptql.scriptqlapi.domain.repositories.UserRepository;
import com.scriptql.scriptqlapi.rest.exceptions.UserNotFoundException;
import com.scriptql.scriptqlapi.rest.mappers.UserMapper;
import com.scriptql.scriptqlapi.utils.Snowflake;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository repository;
    private Snowflake snowflake;

    public User create(UserMapper userMapper) {
        var user = new User();
        var instant = Instant.now().getEpochSecond();

        user.setId(snowflake.next());
        user.setName(userMapper.getName());
        user.setEmail(userMapper.getEmail());
        user.setCreatedAt(instant);
        user.setUpdatedAt(instant);

        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(long id) {
        return repository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public void delete(long id) {
        var user = findById(id);
        repository.delete(user);
    }
}
