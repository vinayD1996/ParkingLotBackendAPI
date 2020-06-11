package com.bridgelabz.parkinglotbackendapi.user.controller;

import com.bridgelabz.parkinglotbackendapi.exception.UserException;
import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.dto.LoginDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.OwnerDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.UserDto;
import com.bridgelabz.parkinglotbackendapi.user.service.IOwnerService;
import com.bridgelabz.parkinglotbackendapi.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/parkinglotowner")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class OwnerController {

    @Autowired
    private IOwnerService iOwnerService;

    @PostMapping("/ownerRegister")
    public ResponseEntity<Response> ownerRegister(@RequestBody OwnerDto ownerDto) throws UserException {
        Response response = iOwnerService.ownerRegister(ownerDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/ownerLogin")
    public ResponseEntity<Response> ownerLogin(@RequestBody LoginDto loginDto) throws UserException {
        Response response = iOwnerService.ownerLogin(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{token}")
    public ResponseEntity<Response> ownerEmailValidation(@PathVariable String token) throws UserException {
        Response response = iOwnerService.ownerValidateEmailId(token);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @PostMapping("/createParking")
    public Response createParking(@RequestParam("parkingLotCapacity") Integer parkingLotCapacity) {
        return iOwnerService.isParkingLotCreate(parkingLotCapacity);

    }
}
