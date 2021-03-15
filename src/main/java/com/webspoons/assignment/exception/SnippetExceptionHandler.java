package com.webspoons.assignment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class SnippetExceptionHandler extends RuntimeException{
    @ExceptionHandler(value
                              = { RuntimeException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Unexpected Error occurred";
        log.error(ex.getMessage());
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
