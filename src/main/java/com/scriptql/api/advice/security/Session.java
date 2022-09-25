package com.scriptql.api.advice.security;

import com.scriptql.api.domain.entities.User;
import com.scriptql.api.domain.errors.UserError;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;

public class Session {

    public static @NotNull User getUser() {
        Object user = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (user == null) {
            throw new UserError("Invalid session");
        }
        return (User) user;
    }

}
