package com.scriptql.scriptqlapi.utils.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "user")
public class User extends AbstractEntity {

    @NotEmpty
    private String login;
    private String name;

    @NotEmpty
    private String password;

}
