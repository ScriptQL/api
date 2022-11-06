package com.scriptql.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scriptql.api.domain.enums.QueryStatus;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "queries")
public class Query extends BaseEntity {

    @ManyToOne
    private User requester;

    @ManyToOne
    private DatabaseConnection connection;

    @NotNull
    private String query;

    @Nullable
    private String description;

    @NotNull
    private QueryStatus status;

    @Lob
    @JsonIgnore
    private byte[] result;

    @Nullable
    @JsonProperty("execution_date")
    @Column(name = "execution_date")
    private Long executionDate;

    @NotNull
    @JsonProperty("created_at")
    @Column(name = "created_at")
    private Long createdAt;

    @NotNull
    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private Long updatedAt;

}
