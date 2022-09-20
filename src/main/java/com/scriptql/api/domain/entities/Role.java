package com.scriptql.api.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    @Column(name = "created_at")
    private Long createdAt;

    @NotNull
    @Column(name = "updated_at")
    private Long updatedAt;

}
