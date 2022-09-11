package com.scriptql.scriptqlapi.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "database_connection_reviewers")
public class DatabaseConnectionReviewer {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "database_connection_id", nullable = false)
    private DatabaseConnection databaseConnection;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
