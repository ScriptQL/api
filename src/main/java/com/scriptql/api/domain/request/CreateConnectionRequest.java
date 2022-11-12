package com.scriptql.api.domain.request;

import com.scriptql.api.domain.enums.DatabaseDriver;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CreateConnectionRequest {

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

    @NotNull
    private Integer port;

    @NotNull
    private DatabaseDriver driver;

}
