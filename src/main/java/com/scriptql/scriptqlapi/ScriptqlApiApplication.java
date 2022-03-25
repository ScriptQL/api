package com.scriptql.scriptqlapi;

import com.scriptql.scriptqlapi.utils.Snowflake;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScriptqlApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScriptqlApiApplication.class, args);
    }

    @Bean
    public Snowflake snowflake() {
        return new Snowflake();
    }

}
