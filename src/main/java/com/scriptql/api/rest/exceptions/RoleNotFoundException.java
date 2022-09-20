package com.scriptql.api.rest.exceptions;

public class RoleNotFoundException extends NotFoundException {
    public RoleNotFoundException() {
        super("Role not found");
    }
}
