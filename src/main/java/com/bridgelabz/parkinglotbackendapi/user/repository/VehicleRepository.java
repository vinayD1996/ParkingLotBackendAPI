package com.bridgelabz.parkinglotbackendapi.user.repository;

import com.bridgelabz.parkinglotbackendapi.user.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    boolean existsByVehiclePlateNumber(String vehiclePlateNumber);

    Vehicle findVehicleByVehiclePlateNumber(String vehiclePlateNumber);

    List<Vehicle> findByEmailId(String emailId);
}
