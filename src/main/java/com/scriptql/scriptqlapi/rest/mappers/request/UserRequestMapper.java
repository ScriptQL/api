package com.scriptql.scriptqlapi.rest.mappers.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRequestMapper {
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
}
