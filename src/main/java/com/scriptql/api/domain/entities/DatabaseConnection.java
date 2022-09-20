package com.scriptql.api.domain.entities;

import com.scriptql.api.domain.enums.DatabaseDriver;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "connections")
public class DatabaseConnection extends BaseEntity {

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String host;

    @NotNull
    @Column(name = "db_name", nullable = false)
    private String database;

    @NotNull
    @Column(nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private Integer port;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DatabaseDriver driver;

}
