package com.scriptql.scriptqlapi.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "query_reviews")
public class QueryReview {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "query_id", nullable = false)
    private Query query;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "review_result")
    private boolean reviewResult;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;
}
