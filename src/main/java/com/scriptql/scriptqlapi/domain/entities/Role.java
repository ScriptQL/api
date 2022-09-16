package com.scriptql.scriptqlapi.domain.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    private long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @Column(name = "created_at")
    private long createdAt;

    @Column(name = "updated_at")
    private long updatedAt;
}
