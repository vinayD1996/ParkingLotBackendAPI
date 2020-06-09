package com.bridgelabz.parkinglotbackendapi.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ToString
public class Response {

    private String statusMessage;
    private int statusCode;
}
