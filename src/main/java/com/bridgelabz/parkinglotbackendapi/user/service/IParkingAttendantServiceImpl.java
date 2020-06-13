package com.bridgelabz.parkinglotbackendapi.user.service;

import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.dto.VehicleDto;
import com.bridgelabz.parkinglotbackendapi.user.model.User;
import com.bridgelabz.parkinglotbackendapi.user.model.Vehicle;
import com.bridgelabz.parkinglotbackendapi.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IParkingAttendantServiceImpl implements  IParkingAtttendantService {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response parkVehicle(VehicleDto vehicleDto) {

        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
        User user = userRepository.findByEmailId(vehicleDto.getEmailId());
        return  null;
    }
}
