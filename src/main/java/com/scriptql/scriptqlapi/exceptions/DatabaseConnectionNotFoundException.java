package com.scriptql.scriptqlapi.exceptions;

public class DatabaseConnectionNotFoundException extends RuntimeException {
    public DatabaseConnectionNotFoundException() {
        super("Datababase connection not found");
    }
}
