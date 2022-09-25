package com.scriptql.api.spring;

import com.scriptql.api.advice.security.AuthorizationFilter;
import com.scriptql.api.advice.security.InvalidAuthProvider;
import com.scriptql.api.services.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityService service;
    private final InvalidAuthProvider provider;

    public SecurityConfig(SecurityService service, InvalidAuthProvider provider) {
        this.service = service;
        this.provider = provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(this.config())
                .and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                // Others
                .anyRequest().authenticated()
                .and()
                // Exception handling
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager(), this.service))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    protected CorsConfigurationSource config() {
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(this.provider);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
