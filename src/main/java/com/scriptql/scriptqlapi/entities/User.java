package com.scriptql.scriptqlapi.entities;

import com.scriptql.scriptqlapi.generic.IEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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

}
