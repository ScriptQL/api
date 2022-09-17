package com.scriptql.scriptqlapi.domain.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "query_reviews")
public class QueryReview {

    @Id
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "query_id", nullable = false)
    private Query query;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "review_result")
    private boolean reviewResult;

    @Column(name = "reviewed_at")
    private long reviewedAt;
}
