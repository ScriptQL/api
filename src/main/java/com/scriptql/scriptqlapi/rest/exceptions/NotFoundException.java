package com.scriptql.scriptqlapi.rest.exceptions;


public class NotFoundException extends RuntimeException {
    NotFoundException(String message) {
        super(message);
    }
}
