package com.scriptql.scriptqlapi.entities;

import com.scriptql.scriptqlapi.enums.DatabaseDriver;
import com.scriptql.scriptqlapi.generic.IEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "database_connections")
public class DatabaseConnection implements IEntity {

    @Id
    private long id;

    @NotEmpty
    private String host;

    @NotEmpty
    private String database;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private int port;

    @Enumerated(EnumType.STRING)
    private DatabaseDriver driver;

}
