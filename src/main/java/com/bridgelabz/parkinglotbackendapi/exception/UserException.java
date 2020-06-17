package com.bridgelabz.parkinglotbackendapi.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserException extends  Exception {

     private int statusCode;
    public UserException(String message ,int statusCode) {

        super(message);
        this.statusCode =statusCode;
    }

}
