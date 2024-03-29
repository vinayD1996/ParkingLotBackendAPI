package com.bridgelabz.parkinglotbackendapi.user.service;


import com.bridgelabz.parkinglotbackendapi.exception.UserException;
import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.user.dto.LoginDto;
import com.bridgelabz.parkinglotbackendapi.user.dto.UserDto;
import com.bridgelabz.parkinglotbackendapi.user.model.User;
import com.bridgelabz.parkinglotbackendapi.user.repository.UserRepository;
import com.bridgelabz.parkinglotbackendapi.utility.JwtTokenUtility;
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
public class IUserServiceImpl  implements  IUserService{

    private Logger logger = LoggerFactory.getLogger(IUserServiceImpl.class);

     @Autowired
     private ModelMapper modelMapper;

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RegistrationMailService registrationMailService;


    @Override
    public Response register(UserDto userDto) throws UserException {

        User user = modelMapper.map(userDto, User.class);
        User userAlreadyPresent = userRepository.findByEmailId(user.getEmailId());
        if (userAlreadyPresent != null) {
            throw new UserException(environment.getProperty("status.register.emailExistError"),401);
        }

        String enCryptedPassword =  bCryptPasswordEncoder.encode(userDto.getPassword());
        user.setPassword(enCryptedPassword);
        Long userId = user.getUserId();
        userRepository.save(user);
        try {
            registrationMailService.sendNotification(user);
        } catch (MailException e) {
            logger.info("Error Sending Email" + e.getMessage());
        }
        return new Response("Parking Lot "+user.getTypeOfActor()+ environment.getProperty("user.register"), 200);

    }

    @Override
    public Response login(LoginDto loginDto) throws UserException {

        User presentUser = checkEmailId(loginDto.getEmailId());
        if(!presentUser.isVerify()){
            throw new UserException(environment.getProperty("status.login.invalidInput"),401);
        }
        boolean passwordStatus = bCryptPasswordEncoder.matches(loginDto.getPassword(), presentUser.getPassword());
        if ( passwordStatus) {
            String token = jwtTokenUtility.createToken(presentUser.getUserId());
            return new Response(environment.getProperty("user.login"), 200);
        } else
            return new Response(environment.getProperty("user.login.password"), 401);
    }

    private User checkEmailId(String emailId) throws UserException {
        User emailIdFromRepository = userRepository.findByEmailId(emailId);
        if (emailIdFromRepository == null)
            throw new UserException(environment.getProperty("user.login.error"),401);
        return emailIdFromRepository;
    }


    @Override
    public Response validateEmailId(String token) {

        Long user_Id = jwtTokenUtility.decodeToken(token);
        User user = userRepository.findByUserId(user_Id);
        user.setVerify(true);
        userRepository.save(user);
        return new Response(environment.getProperty("user.validation"), 200);
    }
}
