package com.quiet.onterview.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class SecurityException extends AuthenticationException {

    HttpStatus httpStatus;
    String exceptionMessage;

    public SecurityException(HttpStatus httpStatus,
                             String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.exceptionMessage = message;
    }
}
