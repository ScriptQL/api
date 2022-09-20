package com.scriptql.api.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "reviewers")
public class Reviewer extends BaseEntity {

    @ManyToOne
    private DatabaseConnection databaseConnection;

    @ManyToOne
    private Role role;

}
