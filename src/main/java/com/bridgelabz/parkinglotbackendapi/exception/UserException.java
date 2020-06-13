package com.bridgelabz.parkinglotbackendapi.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserException extends  Exception {

    public exceptionType type;

    public UserException(exceptionType type ) {
        this.type = type;
    }

    public enum exceptionType {
        USER_NOT_FOUND,
        INVALID_EMAIL_ID,
        USER_ALREADY_EXIST,
        EMAIL_ID_NOT_VERIFIED;
    }
}
