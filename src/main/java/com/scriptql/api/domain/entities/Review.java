package com.scriptql.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @ManyToOne
    private Query query;

    @ManyToOne
    private User user;

    @ManyToOne
    private Role role;

    @Nullable
    private String comment;

    @Nullable
    private Boolean accepted;

    @Nullable
    @JsonProperty("date_reviewed")
    @Column(name = "date_reviewed")
    private Long dateReviewed;

}
