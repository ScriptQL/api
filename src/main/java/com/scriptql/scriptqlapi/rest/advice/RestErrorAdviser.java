package com.scriptql.scriptqlapi.rest.advice;

import com.scriptql.scriptqlapi.rest.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestErrorAdviser {
    private final HttpHeaders httpHeaders = new HttpHeaders();

    public RestErrorAdviser() {
        httpHeaders.add("Content-Type", "application/json");
    }

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<Object> handleIllegalArgument(NotFoundException ex, WebRequest request) {
        return ResponseEntity.notFound()
                .headers(httpHeaders)
                .build();
    }
}
