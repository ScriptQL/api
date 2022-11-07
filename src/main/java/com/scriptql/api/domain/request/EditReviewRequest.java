package com.scriptql.api.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditReviewRequest {

    private long query;
    private String comment;
    private boolean accepted;

}
