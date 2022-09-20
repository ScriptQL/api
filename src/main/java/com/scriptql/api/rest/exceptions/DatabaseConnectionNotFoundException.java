package com.scriptql.api.rest.exceptions;

public class DatabaseConnectionNotFoundException extends NotFoundException {
    public DatabaseConnectionNotFoundException() {
        super("Datababase connection not found");
    }
}
