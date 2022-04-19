package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.entities.User;
import com.scriptql.scriptqlapi.repositories.UserRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService extends AbstractService<User> {

    public UserService(UserRepository repository, Snowflake snowflake) {
        super(repository, snowflake);
    }

    @Override
    public User create(User user) {
        user.setSalt(UUID.randomUUID().toString().substring(0, 25));
        user.setPassword(BCrypt.hashpw(user.getPassword(), user.getSalt()));
        return super.create(user);
    }

}
