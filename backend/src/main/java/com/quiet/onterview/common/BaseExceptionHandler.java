package com.quiet.onterview.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<String> GlobalException(BaseException baseException) {
        return ResponseEntity.status(baseException.getErrorCode().getStatusCode())
                .body(baseException.getErrorCode().getMessage());
    }
}