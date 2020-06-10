package com.bridgelabz.parkinglotbackendapi.user.service;


import com.bridgelabz.parkinglotbackendapi.exception.UserException;
import com.bridgelabz.parkinglotbackendapi.response.Response;
import com.bridgelabz.parkinglotbackendapi.response.ResponseToken;
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
        Optional<User> useralreadyPresent = userRepository.findByEmailId(user.getEmailId());
        if (useralreadyPresent.isPresent()) {
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
    public ResponseToken login(LoginDto loginDto) throws UserException {


        Optional<User> user = userRepository.findByEmailId(loginDto.getEmailId());
        ResponseToken responseToken = new ResponseToken();
        if (user.isPresent()) {
            System.out.println(loginDto.getPassword());
            return authentication(user, loginDto.getPassword());

        }
        return responseToken;
    }

    private ResponseToken authentication(Optional<User> user, String password) throws UserException {

        ResponseToken responseToken = new ResponseToken();
        if (user.get().isVerify()) {
            boolean status = bCryptPasswordEncoder.matches(password, user.get().getPassword());

            if (status == true) {
                String token = jwtTokenUtility.createToken(user.get().getUserId());
                responseToken.setToken(token);
                responseToken.setStatusCode(200);
                responseToken.setStatusMessage("user.login");
                responseToken.setEmailId(user.get().getEmailId());
                responseToken.setFirstName(user.get().getFirstName());
                responseToken.setLastName(user.get().getLastName());
                System.out.println(response);
                return responseToken;
            }

            throw new UserException(UserException.exceptionType.INVALID_PASSWORD);
        }

        throw new UserException(UserException.exceptionType.INVALID_EMAIL_ID);
    }


    @Override
    public Response validateEmailId(String token) throws UserException {

        Long id = jwtTokenUtility.decodeToken(token);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserException.exceptionType.USER_NOT_FOUND));
        user.setVerify(true);
        userRepository.save(user);
        response = ResponseHelper.statusResponse(200, "Successfully verified");
        return response;
    }

}
