package com.scriptql.scriptqlapi.entities;

import com.scriptql.scriptqlapi.interfaces.IEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
public class Role implements IEntity {

    @Id
    private long id;

    @NotEmpty
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
