package com.scriptql.api.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "tokens")
public class Token extends BaseEntity {

    @NotNull
    private Long expires;

    @NotNull
    private String code;

    @NotNull
    @ManyToOne
    private User user;

}
