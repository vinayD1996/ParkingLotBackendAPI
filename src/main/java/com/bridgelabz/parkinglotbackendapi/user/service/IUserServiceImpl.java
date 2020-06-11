package com.bridgelabz.parkinglotbackendapi.user.service;


import com.bridgelabz.parkinglotbackendapi.exception.UserException;
import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.dto.LoginDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.UserDto;
import com.bridgelabz.parkinglotbackendapi.user.model.User;
import com.bridgelabz.parkinglotbackendapi.user.repository.UserRepository;
import com.bridgelabz.parkinglotbackendapi.utility.JwtTokenUtility;
import com.bridgelabz.parkinglotbackendapi.utility.RegistrationMailService;
import com.bridgelabz.parkinglotbackendapi.utility.ResponseHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class IUserServiceImpl  implements  IUserService{

    private Logger logger = LoggerFactory.getLogger(IUserServiceImpl.class);

    @Autowired
     private ModelMapper modelMapper;

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Autowired
    private RegistrationMailService registrationMailService;


    @Autowired
    private Response response;

    @Override
    public Response register(UserDto userDto) throws UserException {

        String emailid = userDto.getEmailId();

        User user = modelMapper.map(userDto, User.class);
        User useralreadyPresent = userRepository.findByEmailId(user.getEmailId());
        if (useralreadyPresent != null) {
            throw new UserException(UserException.exceptionType.USER_ALREADY_EXIST);
        }
        String password =  bCryptPasswordEncoder.encode(userDto.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        Long userId = user.getUserId();
        try {
            registrationMailService.sendNotification(user);
        } catch (MailException e) {
            logger.info("Error Sending Email" + e.getMessage());
        }
        return ResponseHelper.statusResponse(200, "Parking Lot "+user.getTypeOfActor()+" register successfully");


    }

    @Override
    public Response login(LoginDto loginDto) throws UserException {


        User presentUser = checkEmailId(loginDto.getEmailId());
        if(!presentUser.isVerify()){
            throw new UserException(UserException.exceptionType.INVALID_EMAIL_ID);
        }
        boolean status = bCryptPasswordEncoder.matches(loginDto.getPassword(), presentUser.getPassword());
        if (status) {
            String token = jwtTokenUtility.createToken(presentUser.getUserId());
            return new Response("login Successfully", 200);
        } else
            return new Response("Password Incorrect : Unauthorized Access", 401);
    }

    private User checkEmailId(String emailId) throws UserException {
        User userRepositoryByEmailId = userRepository.findByEmailId(emailId);
        if (userRepositoryByEmailId == null)
            throw new UserException(UserException.exceptionType.INVALID_EMAIL_ID);
        return userRepositoryByEmailId;
    }


    @Override
    public Response validateEmailId(String token) throws UserException {

//
        Long user_Id = jwtTokenUtility.decodeToken(token);
        User user = userRepository.findByUserId(user_Id);
        user.setVerify(true);
        userRepository.save(user);
        return new Response("Email Validated Successfully", 200);
    }
}
