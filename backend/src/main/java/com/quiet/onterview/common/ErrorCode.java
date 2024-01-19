package com.quiet.onterview.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * MEMBER
     */
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "EMAIL_DUPLICATED"),
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "NICKNAME_DUPLICATED"),
    PASSWORD_CANNOT_CONFIRM(HttpStatus.BAD_REQUEST, "PASSWORD_CANNOT_CONFIRM"),
    PASSWORD_NOT_MATCHES(HttpStatus.BAD_REQUEST, "PASSWORD_NOT_MATCHES"),
    EMAIL_NOT_EXISTS(HttpStatus.BAD_REQUEST, "EMAIL_NOT_EXISTS"),
    ACCESS_TOKEN_NOT_EXPIRED(HttpStatus.UNAUTHORIZED, "ACCESS_TOKEN_NOT_EXPIRED"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "ACCESS_TOKEN_EXPIRED"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "REFRESH_TOKEN_EXPIRED"),

    /**
     * Video
     */
    VIDEO_NOT_FOUND(HttpStatus.BAD_REQUEST, "VIDEO_NOT_FOUND");


    HttpStatus statusCode;
    String message;
}
