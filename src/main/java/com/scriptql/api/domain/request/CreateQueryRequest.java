package com.scriptql.api.domain.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateQueryRequest {

    @NotEmpty
    private String query;
    private String description;

    private long connection;

}
