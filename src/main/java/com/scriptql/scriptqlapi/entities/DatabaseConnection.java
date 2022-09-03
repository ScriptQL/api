package com.scriptql.scriptqlapi.entities;

import com.scriptql.scriptqlapi.enums.DatabaseDriver;
import com.scriptql.scriptqlapi.interfaces.IEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private DatabaseDriver driver;

    @OneToMany(mappedBy = "database")
    private List<Query> queries;

}
