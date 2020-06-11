package com.bridgelabz.parkinglotbackendapi.user.service;

import com.bridgelabz.parkinglotbackendapi.exception.UserException;
import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.dto.LoginDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.OwnerDto;
import com.bridgelabz.parkinglotbackendapi.user.model.Owner;
import com.bridgelabz.parkinglotbackendapi.user.repository.OwnerRepository;
import com.bridgelabz.parkinglotbackendapi.utility.OwnerJwtTokenUtility;
import com.bridgelabz.parkinglotbackendapi.utility.RegistrationMailService;
import com.bridgelabz.parkinglotbackendapi.utility.ResponseHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IOwnerServiceImpl implements  IOwnerService {

    private Logger logger = LoggerFactory.getLogger(IOwnerServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
     private OwnerJwtTokenUtility ownerJwtTokenUtility;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Autowired
    private RegistrationMailService registrationMailService;


    @Autowired
    private Response response;

    @Override
    public Response ownerRegister(OwnerDto ownerDto) throws UserException {
        String emailid = ownerDto.getEmailId();

        Owner owner = modelMapper.map(ownerDto, Owner.class);
        Owner ownerAlreadyPresent = ownerRepository.findByEmailId(owner.getEmailId());
        if (ownerAlreadyPresent != null) {
            throw new UserException(UserException.exceptionType.USER_ALREADY_EXIST);
        }
        String password =  bCryptPasswordEncoder.encode(ownerDto.getPassword());
        owner.setPassword(password);
        ownerRepository.save(owner);
        Long ownerId= owner.getOwnerId();
        try {
            registrationMailService.sendNotification(owner);
        } catch (MailException e) {
            logger.info("Error Sending Email" + e.getMessage());
        }
        return ResponseHelper.statusResponse(200, "Parking Lot "+owner+" registered successfully");

    }

    @Override
    public Response ownerLogin(LoginDto loginDto) throws UserException {
        Owner presentOwner = checkEmailId(loginDto.getEmailId());
        if(!presentOwner.isVerify()){
            throw new UserException(UserException.exceptionType.INVALID_EMAIL_ID);
        }
        boolean status = bCryptPasswordEncoder.matches(loginDto.getPassword(), presentOwner.getPassword());
        if (status) {
            String token = ownerJwtTokenUtility.createToken(presentOwner.getOwnerId());
            return new Response("login Successfully", 200);
        } else
            return new Response("Password Incorrect : Unauthorized Access", 401);
    }

    private Owner checkEmailId(String emailId) throws UserException {
        Owner ownerRepositoryByEmailId = ownerRepository.findByEmailId(emailId);
        if (ownerRepositoryByEmailId == null)
            throw new UserException(UserException.exceptionType.INVALID_EMAIL_ID);
        return ownerRepositoryByEmailId;
    }

    @Override
    public Response ownerValidateEmailId(String token) {
        Long owner_id = ownerJwtTokenUtility.decodeToken(token);
        Owner owner = ownerRepository.findByOwnerId(owner_id);
        owner.setVerify(true);
        ownerRepository.save(owner);
        return new Response("Email Validated Successfully", 200);
    }

    @Override
    public Response isParkingLotCreate(Integer parkingLotCapacity) {
        return null;
    }
}
