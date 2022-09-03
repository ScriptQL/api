package com.scriptql.scriptqlapi.entities;

import com.scriptql.scriptqlapi.interfaces.IEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements IEntity {

    @Id
    private long id;

    @NotEmpty
    private String login;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    private String salt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Query> queries;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<QueryReview> queryReviews;
}
