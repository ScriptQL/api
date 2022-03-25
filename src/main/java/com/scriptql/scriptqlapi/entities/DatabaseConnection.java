package com.scriptql.scriptqlapi.entities;

import com.scriptql.scriptqlapi.enums.DatabaseDriver;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "database_connection")
@Entity
public class DatabaseConnection {
    @Id
    private long id;
    private String host;
    private String database;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private DatabaseDriver driver;
    private int port;
}
