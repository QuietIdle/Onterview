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
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "이메일이 중복되었습니다"),
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "닉네임이 중복되었습니다"),
    PASSWORD_CANNOT_CONFIRM(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 확인이 일치하지 않습니다"),
    PASSWORD_NOT_MATCHES(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    EMAIL_NOT_EXISTS(HttpStatus.BAD_REQUEST, "해당 이메일을 가진 유저가 없습니다"),
    ACCESS_TOKEN_NOT_EXPIRED(HttpStatus.UNAUTHORIZED, "ACCESS TOKEN이 만료되지 않았습니다"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "ACCESS TOKEN이 만료되었습니다"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "REFRESH TOKEN이 만료되었습니다"),

    /**
     * Question
     */
    MY_QUESTION_FOLDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MY_QUESTION_FOLDER_NOT_FOUND"),
    MY_QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "MY_QUESTION_NOT_FOUND"),
    MY_QUESTION_FOLDER_NOT_MATCH(HttpStatus.BAD_REQUEST, "MY_QUESTION_FOLDER_NOT_MATCH"),
    COMMON_QUESTION_FOLDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMON_QUESTION_FOLDER_NOT_FOUND"),
    COMMON_QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMON_QUESTION_NOT_FOUND"),

    /**
     * Video
     */
    VIDEO_NOT_FOUND(HttpStatus.BAD_REQUEST, "VIDEO_NOT_FOUND"),

    /**
     * File
     */
    FILE_NOT_EXIST(HttpStatus.BAD_REQUEST, "FILE_NOT_EXIST");


    HttpStatus statusCode;
    String message;

}
