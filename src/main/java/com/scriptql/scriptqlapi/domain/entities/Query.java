package com.scriptql.scriptqlapi.domain.entities;

import com.scriptql.scriptqlapi.domain.enums.QueryStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "queries")
public class Query {

    @Id
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "database_id", nullable = false)
    private DatabaseConnection database;

    private String query;

    private String description;

    private QueryStatus status;

    @Lob
    private byte[] result;

    @Column(name = "execution_date")
    private long executionDate;

    @Column(name = "created_at")
    private long createdAt;

    @Column(name = "updated_at")
    private long updatedAt;

    @OneToMany(mappedBy = "query")
    private List<QueryReview> queryReviews;
}
