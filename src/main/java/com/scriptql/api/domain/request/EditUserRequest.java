package com.scriptql.api.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EditUserRequest {

    private String name;
    private String email;
    private Set<Long> roles;
    private String status;
    private String password;

}
