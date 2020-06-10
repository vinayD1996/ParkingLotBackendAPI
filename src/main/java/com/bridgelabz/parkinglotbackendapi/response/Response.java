package com.bridgelabz.parkinglotbackendapi.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Response {

    private String statusMessage;
    private int statusCode;


    public Response(String statusMessage, int statusCode) {
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
    }

}
