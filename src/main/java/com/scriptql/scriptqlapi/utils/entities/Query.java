package com.scriptql.scriptqlapi.utils.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "query")
public class Query extends AbstractEntity {

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
