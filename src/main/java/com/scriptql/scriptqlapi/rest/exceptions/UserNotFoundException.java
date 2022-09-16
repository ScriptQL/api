package com.scriptql.scriptqlapi.rest.exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User not found");
    }
}
