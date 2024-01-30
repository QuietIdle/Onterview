package com.quiet.onterviewstorage.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalController {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseExceptionResponse> handleBaseException(BaseException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(new BaseExceptionResponse(
                        e.getErrorCode().getStatus().value(),
                        e.getErrorCode().getMessage()
                ));
    }
}
