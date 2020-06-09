package com.bridgelabz.parkinglotbackendapi.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ToString
public class ResponseToken {

    private String statusMessage;
    private int statusCode;
    private String token;
    private String emailId;
    private String firstName;
    private String lastName;

}
