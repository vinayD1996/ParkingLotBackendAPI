package com.bridgelabz.parkinglotbackendapi.response;


import lombok.*;



@Data
@AllArgsConstructor
public class Response {

    private String statusMessage;
    private int statusCode;
}
