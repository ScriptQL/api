package com.scriptql.scriptqlapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String name;

    private String host;

    private String database;

    private String username;

    @JsonIgnore
    private String password;

    private int port;

    @Column(name = "created_at")
    private long createdAt;

    @Column(name = "updated_at")
    private long updatedAt;

    @Enumerated(EnumType.STRING)
    private DatabaseDriver driver;

    @OneToMany(mappedBy = "databaseConnection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DatabaseConnectionReviewer> databaseConnectionReviewers = new ArrayList<>();

}
