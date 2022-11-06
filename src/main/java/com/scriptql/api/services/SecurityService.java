package com.scriptql.api.services;

import com.scriptql.api.advice.Snowflake;
import com.scriptql.api.domain.entities.Token;
import com.scriptql.api.domain.entities.User;
import com.scriptql.api.domain.errors.SecurityError;
import com.scriptql.api.domain.repositories.TokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SecurityService {

    private final TokenRepository repository;

    public SecurityService(TokenRepository repository) {
        this.repository = repository;
    }

    public User parse(String code) throws SecurityError {
        Token token = this.repository.findByCode(code)
                .orElseThrow(() -> new SecurityError(401, "Invalid token"));
        if (this.isExpired(token)) {
            this.repository.delete(token);
            throw new SecurityError(401, "Expired token");
        }
        return token.getUser();
    }

    @Transactional
    public Token create(User user) throws SecurityError {
        Token token = new Token();
        token.setCode(this.issueId());
        token.setExpires(this.getExpiration());
        token.setUser(user);
        return this.repository.save(token);
    }

    private boolean isExpired(Token token) {
        if (token.getExpires() < System.currentTimeMillis()) {
            return true;
        }
        long created = Snowflake.getInstance().toInstant(token.getId()).toEpochSecond() * 1000;

        Long last = token.getUser().getLastSecurityEvent();
        if (last == null) {
            return false;
        } else {
            return last > created;
        }
    }

    private long getExpiration() {
        return System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);
    }

    private String issueId() {
        return "scql." + UUID.randomUUID().toString().replace("-", "");
    }

}
