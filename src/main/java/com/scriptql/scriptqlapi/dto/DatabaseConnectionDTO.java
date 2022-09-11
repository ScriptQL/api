package com.scriptql.scriptqlapi.dto;

import com.scriptql.scriptqlapi.enums.DatabaseDriver;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class DatabaseConnectionDTO {

    @NotEmpty
    private String name;
    @NotEmpty
    private String host;
    @NotEmpty
    private String database;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private int port;
    private DatabaseDriver driver;
    private Set<String> roles;
}
