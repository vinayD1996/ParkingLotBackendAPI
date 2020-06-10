package com.bridgelabz.parkinglotbackendapi.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserException extends  Exception {

    public exceptionType type;
//       int code;
    public UserException(exceptionType type) {
        this.type = type;

    }

    public enum exceptionType {
        USER_NOT_FOUND,
        INVALID_EMAIL_ID,
        INVALID_PASSWORD,
        USER_ALREADY_EXIST,
        EMAIL_ALREADY_VERIFIED;

    }
}
