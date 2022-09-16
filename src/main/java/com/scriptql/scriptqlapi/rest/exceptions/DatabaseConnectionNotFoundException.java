package com.scriptql.scriptqlapi.rest.exceptions;

public class DatabaseConnectionNotFoundException extends NotFoundException {
    public DatabaseConnectionNotFoundException() {
        super("Datababase connection not found");
    }
}
