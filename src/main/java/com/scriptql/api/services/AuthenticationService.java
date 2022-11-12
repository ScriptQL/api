package com.scriptql.api.services;

import com.scriptql.api.domain.entities.Token;
import com.scriptql.api.domain.entities.User;
import com.scriptql.api.domain.enums.UserGroup;
import com.scriptql.api.domain.errors.SecurityError;
import com.scriptql.api.domain.errors.UserError;
import com.scriptql.api.domain.repositories.UserRepository;
import com.scriptql.api.domain.request.LoginRequest;
import com.scriptql.api.domain.request.RegisterRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository users;
    private final BCryptPasswordEncoder bcrypt;
    private final SecurityService security;

    public AuthenticationService(UserRepository users, BCryptPasswordEncoder bcrypt, SecurityService security) {
        this.users = users;
        this.bcrypt = bcrypt;
        this.security = security;
    }

    public Token login(LoginRequest request) throws SecurityError {
        User user = this.users.findByEmail(request.getEmail())
                .orElseThrow(() -> new SecurityError(401, "Invalid credentials"));
        if (!this.bcrypt.matches(request.getPassword(), user.getPassword())) {
            throw new SecurityError(401, "Invalid credentials");
        } else if (this.bcrypt.upgradeEncoding(user.getPassword())) {
            user.setPassword(this.bcrypt.encode(request.getPassword()));
            this.users.save(user);
        }
        return this.security.create(user);
    }

    public Token register(RegisterRequest request) throws SecurityError {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new UserError("Invalid email");
        } else if (request.getName() == null || request.getName().isBlank()) {
            throw new UserError("Invalid name");
        } else if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new UserError("Invalid password");
        } else if (this.users.findByEmail(request.getEmail()).isPresent()) {
            throw new UserError("Email already in use");
        }
        User user = new User();
        if (this.users.count() == 0 ) {
            user.setAccess(UserGroup.ADMIN);
        } else {
            user.setAccess(UserGroup.USER);
        }
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(this.bcrypt.encode(request.getPassword()));
        user.setLastSecurityEvent(System.currentTimeMillis());
        this.users.save(user);

        return this.security.create(user);
    }

}
