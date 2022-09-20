package com.scriptql.api.rest.exceptions;


public class NotFoundException extends RuntimeException {
    NotFoundException(String message) {
        super(message);
    }
}
