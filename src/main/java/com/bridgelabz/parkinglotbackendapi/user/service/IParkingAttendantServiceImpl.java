package com.bridgelabz.parkinglotbackendapi.user.service;

import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.dto.VehicleDto;
import com.bridgelabz.parkinglotbackendapi.user.model.ParkingLot;
import com.bridgelabz.parkinglotbackendapi.user.model.Vehicle;
import com.bridgelabz.parkinglotbackendapi.user.repository.ParkingLotRepository;
import com.bridgelabz.parkinglotbackendapi.user.repository.ParkingLotSystemRepository;
import com.bridgelabz.parkinglotbackendapi.user.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.toCollection;

@Service
public class IParkingAttendantServiceImpl implements  IParkingAtttendantService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ParkingLotSystemRepository parkingLotSystemRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    VehicleRepository vehicleRepository;


    @Override
    public Response parkVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
        List<ParkingLot> lotsList = parkingLotRepository.findAll();
        ParkingLot parkingLot = getParkingLot(lotsList);
        if (parkingLot.getAvailableSlots() > 0 && checkVehicleAvailable(vehicle.getVehiclePlateNumber())) {
            vehicle.setAttendantName(parkingLot.getAttendantName());
            vehicle.setParkingLot(parkingLot);
            vehicle.setSlotNumber(parkingLot.getVehicleList().size()+1);
            parkingLot.addVehicle(vehicle);
            parkingLot.setAvailableSlots(parkingLot.getAvailableSlots() - 1);
            vehicleRepository.save(vehicle);
            parkingLotRepository.save(parkingLot);
            return new Response("Vehicle Parked", 202);
        } else if (!checkVehicleAvailable(vehicle.getVehiclePlateNumber()))
            return new Response("Vehicle already parked", 202);
        return new Response("All parkingLots are Full ", 202);
    }

    @Override
    public Response unParkVehicle(String vehiclePlateNumber) {
        if (!checkVehicleAvailable(vehiclePlateNumber)) {
            Vehicle byVehicleNumber = vehicleRepository.findVehicleByVehiclePlateNumber(vehiclePlateNumber);
            ParkingLot parkingLot = parkingLotRepository.findByAttendantName(byVehicleNumber.getAttendantName());
            parkingLot.setAvailableSlots(parkingLot.getAvailableSlots() + 1);
            parkingLot.removeVehicle(byVehicleNumber);
            parkingLotRepository.save(parkingLot);
            vehicleRepository.delete(byVehicleNumber);
            return new Response("Vehicle unParked Successfully", 202);
        }
        return new Response("Vehicle not parked here", 404);
    }

    private boolean checkVehicleAvailable(String vehiclePlateNumber) {
        return !vehicleRepository.existsByVehiclePlateNumber(vehiclePlateNumber);
    }

    public ParkingLot getParkingLot(List<ParkingLot> lotsList) {
        ParkingLot parkingLot= (ParkingLot) lotsList.stream().
                sorted(Comparator.comparing(ParkingLot::getAvailableSlots).reversed()) .
                collect(toCollection(ArrayList::new)).
                toArray()[0];
        return parkingLot;
    }
}
