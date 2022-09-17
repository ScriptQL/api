package com.scriptql.scriptqlapi.domain.entities;

import com.scriptql.scriptqlapi.domain.enums.DatabaseDriver;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "database_connections")
public class DatabaseConnection {

    @Id
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private String database;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int port;

    @Column(name = "created_at", nullable = false)
    private long createdAt;

    @Column(name = "updated_at", nullable = false)
    private long updatedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DatabaseDriver driver;

    @OneToMany(mappedBy = "databaseConnection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DatabaseConnectionReviewer> databaseConnectionReviewers = new ArrayList<>();

}
