package com.scriptql.api.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String name;
    private String email;
    private String password;

    @Column(name = "last_security_event")
    private Long lastSecurityEvent;

}
