package com.scriptql.api.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditReviewRequest {

    private String comment;
    private boolean accepted;

}
