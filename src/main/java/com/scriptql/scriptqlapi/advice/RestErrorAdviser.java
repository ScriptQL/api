package com.scriptql.scriptqlapi.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@ControllerAdvice
public class RestErrorAdviser {
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public RestErrorAdviser() {
        httpHeaders.add("Content-Type", "application/json");
    }

    public static String createError(int code, String message) {
        try {
            RestError restError = new RestError(code, message);
            return objectMapper.writeValueAsString(restError);
        } catch (IOException ex) {
            return message;
        }
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return ResponseEntity.badRequest()
                .headers(httpHeaders)
                .body(createError(400, ex.getMessage()));
    }
}
