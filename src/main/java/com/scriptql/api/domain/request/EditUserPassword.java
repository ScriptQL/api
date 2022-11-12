package com.scriptql.api.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserPassword {

    private String currentPwd;
    private String newPwd;
    private String newPwdConfirmation;

}
