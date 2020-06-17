package com.bridgelabz.parkinglotbackendapi.user.service;

import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.RabbitMq.RabbitMqImplementation;
import com.bridgelabz.parkinglotbackendapi.user.dto.VehicleDto;
import com.bridgelabz.parkinglotbackendapi.user.model.Email;
import com.bridgelabz.parkinglotbackendapi.user.model.ParkingLot;
import com.bridgelabz.parkinglotbackendapi.user.model.Vehicle;
import com.bridgelabz.parkinglotbackendapi.user.repository.ParkingLotRepository;
import com.bridgelabz.parkinglotbackendapi.user.repository.ParkingLotSystemRepository;
import com.bridgelabz.parkinglotbackendapi.user.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.toCollection;

@Service
@PropertySource("classpath:message.properties")
public class IParkingAttendantServiceImpl implements  IParkingAtttendantService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ParkingLotSystemRepository parkingLotSystemRepository;

    @Autowired
    private Environment environment;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    private RabbitMqImplementation rabbitMqImplementation;

    @Autowired
    private Email email;


    @Override
    public Response parkVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
        List<ParkingLot> lotsList = parkingLotRepository.findAll();
        ParkingLot parkingLot = getParkingLot(lotsList);
        vehicle.setParkingLot(parkingLot);
        if (parkingLot.getAvailableSlots() > 0 && checkVehicleAvailable(vehicle.getVehiclePlateNumber())) {
            vehicle.setAttendantName(parkingLot.getAttendantName());
            vehicle.setSlotNumber(parkingLot.getVehicleList().size()+1);
            parkingLot.addVehicle(vehicle);
            parkingLot.setAvailableSlots(parkingLot.getAvailableSlots() - 1);
            vehicleRepository.save(vehicle);
            parkingLotRepository.save(parkingLot);
            return new Response(environment.getProperty("status.parkVehicle.updatedSuccessful"), 202);
        } else if (!checkVehicleAvailable(vehicle.getVehiclePlateNumber()))
            return new Response(environment.getProperty("status.alreadyParkVehicle.updatedSuccessful"), 202);

           email.setEmailTo(vehicle.getEmailId());
           email.setSubject("ParkingLot SlotsAvailability Information");
           email.setMessage("Parking Lot"+vehicle.getParkingLot().getLotId() + "is Full");
           rabbitMqImplementation.sendMessageToQueue(email);
           rabbitMqImplementation.send(email);
        return new Response(environment.getProperty("status.parkVehicle.updateCapacityFull"), 202);
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
            return new Response(environment.getProperty("status.unParkVehicle.updatedSuccessful"), 202);
        }
        return new Response(environment.getProperty("status.unParkVehicle.error"), 404);
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
