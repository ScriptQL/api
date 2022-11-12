package com.scriptql.api.domain.entities;

import com.scriptql.api.domain.enums.UserGroup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String name;
    private String email;
    private String password;

    private String status;

    @Enumerated(EnumType.STRING)
    private UserGroup access;

    @Column(name = "last_security_event")
    private Long lastSecurityEvent;

}
