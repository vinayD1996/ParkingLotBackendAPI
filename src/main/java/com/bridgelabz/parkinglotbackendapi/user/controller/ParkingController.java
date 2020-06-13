package com.bridgelabz.parkinglotbackendapi.user.controller;



import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.dto.VehicleDto;
import com.bridgelabz.parkinglotbackendapi.user.service.IParkingAtttendantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/park")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class ParkingController {

    @Autowired
    private IParkingAtttendantService iParkingAtttendantService;

    @PostMapping("/parkVehicle")
    public ResponseEntity<Response> parkVehicle(@RequestBody VehicleDto vehicleDto) {
        Response response = iParkingAtttendantService.parkVehicle(vehicleDto);
        return  new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}
