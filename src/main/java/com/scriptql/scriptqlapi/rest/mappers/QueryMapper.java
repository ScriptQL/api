package com.scriptql.scriptqlapi.rest.mappers;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class QueryMapper {
    @NotEmpty
    private String query;
    private String description;
    private long databaseConnectionId;
    private long userId;
}
