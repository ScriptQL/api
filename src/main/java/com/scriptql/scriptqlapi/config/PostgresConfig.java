package com.scriptql.scriptqlapi.config;

import com.scriptql.scriptqlapi.repositories.DatabaseConnectionRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.scriptql.scriptqlapi.entities")
@EnableJpaRepositories(basePackageClasses = DatabaseConnectionRepository.class)
public class PostgresConfig {}
