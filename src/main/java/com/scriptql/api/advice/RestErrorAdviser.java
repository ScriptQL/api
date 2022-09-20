package com.scriptql.api.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scriptql.api.domain.errors.UserError;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@ControllerAdvice
public class RestErrorAdviser {
    private final HttpHeaders headers = new HttpHeaders();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestErrorAdviser() {
        headers.add("Content-Type", "application/json");
    }

    private String createError(int code, String message) {
        try {
            return objectMapper.writeValueAsString(new RestError(code, message));
        } catch (IOException ignored) {
            return message;
        }
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return ResponseEntity.badRequest().headers(headers).body(this.createError(400, ex.getMessage()));
    }

    @ExceptionHandler(value = UserError.class)
    protected ResponseEntity<Object> handleUser(UserError ex, WebRequest request) {
        return ResponseEntity.badRequest().headers(headers).body(this.createError(400, ex.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        return ResponseEntity.badRequest().headers(headers).body(this.createError(400, ex.getMessage()));
    }

    @Data
    @AllArgsConstructor
    public static class RestError {

        private int code;
        private String message;

    }

}
