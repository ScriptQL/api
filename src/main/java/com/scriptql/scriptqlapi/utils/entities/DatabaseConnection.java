package com.scriptql.scriptqlapi.utils.entities;

import com.scriptql.scriptqlapi.enums.DatabaseDriver;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "database_connection")
public class DatabaseConnection extends AbstractEntity {

    private String host;
    private String database;
    private String username;
    private String password;
    private int port;

    @Enumerated(EnumType.STRING)
    private DatabaseDriver driver;

}
