package com.quiet.onterview.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<BaseExceptionResponse> GlobalException(BaseException baseException) {
        return ResponseEntity.status(baseException.getErrorCode().getStatusCode())
                .body(BaseExceptionResponse.builder()
                        .errorCode(baseException.getErrorCode().getStatusCode().value())
                        .errorMessage(baseException.getErrorCode().getMessage())
                        .build());
    }
}