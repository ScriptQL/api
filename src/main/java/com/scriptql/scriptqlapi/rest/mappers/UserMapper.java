package com.scriptql.scriptqlapi.rest.mappers;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserMapper {
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
}
