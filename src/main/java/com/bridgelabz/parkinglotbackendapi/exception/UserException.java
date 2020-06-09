package com.bridgelabz.parkinglotbackendapi.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserException extends  Exception {

    int code;
    String msg;
    public UserException(String msg)
    {
        super(msg);
    }

    public UserException(int code, String msg)
    {
        super(msg);
        this.code =code;
    }
}
