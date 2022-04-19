package com.scriptql.scriptqlapi.services;

import com.scriptql.scriptqlapi.repositories.UserRepository;
import com.scriptql.scriptqlapi.utils.Snowflake;
import com.scriptql.scriptqlapi.utils.entities.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User> {

    private static final String SALT = "PAO DE BATATA COM AZEITONA";

    public UserService(UserRepository repository, Snowflake snowflake) {
        super(repository, snowflake);
    }

    @Override
    public User create(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), SALT));
        return super.create(user);
    }

}
