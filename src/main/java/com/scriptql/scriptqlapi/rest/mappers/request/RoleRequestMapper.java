package com.scriptql.scriptqlapi.rest.mappers.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RoleRequestMapper {
    @NotEmpty
    private String name;
}
