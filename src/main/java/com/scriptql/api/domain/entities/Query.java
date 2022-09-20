package com.scriptql.api.domain.entities;

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
    private byte[] result;

    @Nullable
    @Column(name = "execution_date")
    private Long executionDate;

    @NotNull
    @Column(name = "created_at")
    private Long createdAt;

    @NotNull
    @Column(name = "updated_at")
    private Long updatedAt;

}
