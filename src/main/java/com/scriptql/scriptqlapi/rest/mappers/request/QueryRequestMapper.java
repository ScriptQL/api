package com.scriptql.scriptqlapi.rest.mappers.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class QueryRequestMapper {
    @NotEmpty
    private String query;
    private String description;
    private long databaseConnectionId;
    private long userId;
}
