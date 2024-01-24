package com.quiet.onterview.security.exception;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleSecurityException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("DSfdsf");
    }
}
