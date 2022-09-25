package com.scriptql.api.advice.security;

import com.scriptql.api.advice.RestErrorAdviser;
import com.scriptql.api.domain.entities.User;
import com.scriptql.api.domain.errors.SecurityError;
import com.scriptql.api.services.SecurityService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final SecurityService service;

    public AuthorizationFilter(AuthenticationManager manager, SecurityService service) {
        super(manager);
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication =
                    this.getAuthentication(header.substring("Bearer ".length()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SecurityError ex) {
            RestErrorAdviser.filterException(res, ex, ex.getCode());
            return;
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) throws SecurityError {
        User user = this.service.parse(token);
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }

}
