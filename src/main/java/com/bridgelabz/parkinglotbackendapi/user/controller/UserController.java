package com.bridgelabz.parkinglotbackendapi.user.controller;

import com.bridgelabz.parkinglotbackendapi.exception.UserException;
import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.response.ResponseToken;
import com.bridgelabz.parkinglotbackendapi.user.dto.LoginDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.UserDto;
import com.bridgelabz.parkinglotbackendapi.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestBody("/user")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody UserDto userDto) {
        Response response = userService.register(userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseToken> Login(@RequestBody LoginDto loginDto) {
        System.out.println("in login controller");
        ResponseToken response = userService.login(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{token}/valid")
    public ResponseEntity<Response> emailValidation(@PathVariable String token) {
        Response response = userService.validateEmailId(token);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

}
