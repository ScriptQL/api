package com.scriptql.api.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scriptql.api.domain.errors.SecurityError;
import com.scriptql.api.domain.errors.UserError;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class RestErrorAdviser {
    private final HttpHeaders headers = new HttpHeaders();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public RestErrorAdviser() {
        headers.add("Content-Type", "application/json");
    }

    public static void filterException(HttpServletResponse response, Exception exception, int status) {
        try {
            response.getWriter().print(createError(status, exception.getMessage()));
            response.addHeader("Content-Type", "application/json");
            response.setStatus(status);
        } catch (IOException ignored) {
            // Ignored
        }
    }

    private static String createError(int code, String message) {
        try {
            return objectMapper.writeValueAsString(new RestError(code, message));
        } catch (IOException ignored) {
            return message;
        }
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return ResponseEntity.badRequest().headers(headers).body(createError(400, ex.getMessage()));
    }

    @ExceptionHandler(value = UserError.class)
    protected ResponseEntity<Object> handleUser(UserError ex, WebRequest request) {
        return ResponseEntity.badRequest().headers(headers).body(createError(400, ex.getMessage()));
    }

    @ExceptionHandler(value = SecurityError.class)
    protected ResponseEntity<Object> handleUser(SecurityError ex, WebRequest request) {
        return ResponseEntity.status(ex.getCode()).headers(headers).body(createError(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        return ResponseEntity.badRequest().headers(headers).body(createError(400, ex.getMessage()));
    }

    @Data
    @AllArgsConstructor
    public static class RestError {

        private int code;
        private String message;

    }

}
