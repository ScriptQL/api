package com.scriptql.scriptqlapi.entities;

import com.scriptql.scriptqlapi.generic.IEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "queries")
public class Query implements IEntity {

    @Id
    private long id;

    @NotEmpty
    private String query;

    @NotEmpty
    @ManyToOne
    private User createdBy;

    @NotEmpty
    @ManyToMany
    private List<DatabaseConnection> connections;

    private LocalDateTime executionDate;

}
