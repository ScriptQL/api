package com.scriptql.api.domain.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}
