package com.scriptql.scriptqlapi.entities;

import com.scriptql.scriptqlapi.enums.DatabaseDriver;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "database_connections")
public class DatabaseConnection {

    @Id
    private long id;

    private String name;

    private String host;

    private String database;

    private String username;

    private String password;

    private int port;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private DatabaseDriver driver;

    @OneToMany(mappedBy = "database")
    private List<Query> queries = new ArrayList<>();

    @OneToMany(mappedBy = "databaseConnection", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<DatabaseConnectionReviewer> databaseConnectionReviewers = new ArrayList<>();

}
