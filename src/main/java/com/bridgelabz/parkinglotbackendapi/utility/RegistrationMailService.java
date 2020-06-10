package com.bridgelabz.parkinglotbackendapi.utility;

import com.bridgelabz.parkinglotbackendapi.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RegistrationMailService {

    @Autowired
    private  JwtTokenUtility jwtTokenUtility;

    private JavaMailSender mailSender;

    @Autowired
    public RegistrationMailService(JavaMailSender mailSender) {

        this.mailSender = mailSender;
    }

    public void sendNotification(User user) throws MailException{

        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(user.getEmailId());
        mailMessage.setFrom("gangishettyvinay@gmail.com");
        mailMessage.setSubject("Thank you For Registering With Us : " + user.getFirstName()+user.getLastName());
        mailMessage.setText("please click on the link to verify yourself : "+"http://localhost:8080/parkinglot/"+jwtTokenUtility.createToken(user.getUserId()));

        mailSender.send(mailMessage);
    }

}
