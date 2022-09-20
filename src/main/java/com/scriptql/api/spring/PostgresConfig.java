package com.scriptql.api.spring;

import com.scriptql.api.domain.repositories.ConnectionRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.scriptql.api.domain.entities")
@EnableJpaRepositories(basePackageClasses = ConnectionRepository.class)
public class PostgresConfig {}
