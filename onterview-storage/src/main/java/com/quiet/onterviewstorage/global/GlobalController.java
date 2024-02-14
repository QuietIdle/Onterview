package com.quiet.onterviewstorage.global;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalController {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseExceptionResponse> handleBaseException(BaseException e) {
        log.info("# error: " + e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(new BaseExceptionResponse(
                        e.getErrorCode().getStatus().value(),
                        e.getErrorCode().getMessage()
                ));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<BaseExceptionResponse> handleIOException(IOException e) {
        log.info("# IO error: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new BaseExceptionResponse(
                        HttpStatus.FORBIDDEN.value(),
                        e.getMessage()
                ));
    }
}
