package com.bridgelabz.parkinglotbackendapi.user.service;

import com.bridgelabz.parkinglotbackendapi.exception.UserException;
import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.dto.LoginDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.OwnerDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.ParkingLotDto;
import com.bridgelabz.parkinglotbackendapi.user.model.Owner;
import com.bridgelabz.parkinglotbackendapi.user.model.ParkingLot;
import com.bridgelabz.parkinglotbackendapi.user.model.ParkingLotSystem;
import com.bridgelabz.parkinglotbackendapi.user.repository.OwnerRepository;
import com.bridgelabz.parkinglotbackendapi.user.repository.ParkingLotRepository;
import com.bridgelabz.parkinglotbackendapi.user.repository.ParkingLotSystemRepository;
import com.bridgelabz.parkinglotbackendapi.utility.OwnerJwtTokenUtility;
import com.bridgelabz.parkinglotbackendapi.utility.RegistrationMailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:message.properties")
public class IOwnerServiceImpl implements  IOwnerService {

    private Logger logger = LoggerFactory.getLogger(IOwnerServiceImpl.class.getName());

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OwnerJwtTokenUtility ownerJwtTokenUtility;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private ParkingLotSystemRepository parkingLotSystemRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Autowired
    private RegistrationMailService registrationMailService;


    @Override
    public Response ownerRegister(OwnerDto ownerDto) throws UserException {

        Owner owner = modelMapper.map(ownerDto, Owner.class);
        Owner isOwnerPresent = ownerRepository.findByEmailId(owner.getEmailId());
        if (isOwnerPresent != null) {
            throw new UserException(environment.getProperty("status.register.emailExistError"),401);
        }

        String enCryptedPassword =  bCryptPasswordEncoder.encode(ownerDto.getPassword());
        owner.setPassword(enCryptedPassword);

        ownerRepository.save(owner);
        try {
            registrationMailService.sendNotification(owner);
        } catch (MailException e) {
            logger.info("Error Sending Email" + e.getMessage());
        }
        return new Response("Parking Lot owner"+ environment.getProperty("user.register"), 200);

    }


    @Override
    public Response ownerLogin(LoginDto loginDto) throws UserException {

        Owner isOwnerPresent = checkEmailId(loginDto.getEmailId());
        if(!isOwnerPresent.isVerify()){
            throw new UserException(environment.getProperty("status.login.invalidInput"),401);
        }

        boolean status = bCryptPasswordEncoder.matches(loginDto.getPassword(), isOwnerPresent.getPassword());
        if (status) {
            String token = ownerJwtTokenUtility.createToken(isOwnerPresent.getOwnerId());
            return new Response(environment.getProperty("user.login"), 200);
        } else
            return new Response(environment.getProperty("user.login.password"), 401);
    }

    private Owner checkEmailId(String emailId) throws UserException {
        Owner ownerEmailId = ownerRepository.findByEmailId(emailId);
        if (ownerEmailId == null)
            throw new UserException(environment.getProperty("user.login.error"),401);
        return ownerEmailId;
    }

    @Override
    public Response ownerValidateEmailId(String token) {
        Long owner_id = ownerJwtTokenUtility.decodeToken(token);
        Owner owner = ownerRepository.findByOwnerId(owner_id);
        owner.setVerify(true);
        ownerRepository.save(owner);
        return new Response(environment.getProperty("user.validation"), 200);
    }

    @Override
    public Response createParkingLot(ParkingLotDto parkingLotDto) throws UserException {

        Owner owner = ownerRepository.findByEmailId(parkingLotDto.getLoginDto().getEmailId());
        if (ownerLogin(parkingLotDto.getLoginDto()).getStatusCode() == 200) {
            for (int i = 0; i < parkingLotDto.getNumberOfLotSystems(); i++) {
                ParkingLotSystem parkingLotSystem = new ParkingLotSystem(i);
                parkingLotSystem.setNumberOfLots(parkingLotDto.getNumberOfLots());
                owner.addParkingLotSystems(parkingLotSystem);
                parkingLotSystem.setOwner(owner);
                parkingLotSystemRepository.save(parkingLotSystem);
                createLot(parkingLotDto, owner,parkingLotSystem);
            }
            owner.setNumberOfLotSytems(parkingLotSystemRepository.findAllByOwner(owner).size());
            ownerRepository.save(owner);
            return new Response(environment.getProperty("status.parkingLot.createdSuccessful"), 201);
        }
        throw new UserException(environment.getProperty("user.login.error"),401);
    }

    private void createLot(ParkingLotDto parkingLotDto, Owner owner, ParkingLotSystem parkingLotSystem) {

        int j = 1;
        for (int i = 0; i < parkingLotDto.getNumberOfLots(); i++) {
            ParkingLot parkingLot = new ParkingLot(j);
            parkingLot.setVacant(true);
            parkingLot.setNumberOfSlots(parkingLotDto.getNumberOfSlots()[i]);
            parkingLot.setAttendantName(parkingLotDto.getAttendantName()[i]);
            parkingLot.setAvailableSlots(parkingLotDto.getNumberOfSlots()[i]);
            parkingLotSystem.addParkingLot(parkingLot);
            parkingLot.setParkingLotSystem(parkingLotSystem);
            parkingLot.setOwner(owner);
            parkingLotRepository.save(parkingLot);
            j++;
        }
    }
}