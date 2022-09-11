package com.scriptql.scriptqlapi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "queries")
public class Query {

    @Id
    private long id;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "database_id", nullable = false)
    private DatabaseConnection database;

    @NotEmpty
    private String query;

    @NotEmpty
    private String description;

    @Column(name = "execution_date")
    private LocalDateTime executionDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "query")
    private Set<QueryReview> queryReviews;
}
