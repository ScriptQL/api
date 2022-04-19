package com.scriptql.scriptqlapi.entities;

import com.scriptql.scriptqlapi.enums.DatabaseDriver;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "database_connections")
public class DatabaseConnection implements IEntity {

    @Id
    private long id;
    private String host;
    private String database;
    private String username;
    private String password;
    private int port;

    @Enumerated(EnumType.STRING)
    private DatabaseDriver driver;

}
