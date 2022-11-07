package com.scriptql.api.domain.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class CreateQueryRequest {

    @NotEmpty
    private String query;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    private long connection;
    private List<Long> reviewers;

}
