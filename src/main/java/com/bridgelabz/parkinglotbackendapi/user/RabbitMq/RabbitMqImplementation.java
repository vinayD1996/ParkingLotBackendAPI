package com.bridgelabz.parkinglotbackendapi.user.RabbitMq;


import com.bridgelabz.parkinglotbackendapi.user.model.Email;
import com.bridgelabz.parkinglotbackendapi.utility.RegistrationMailService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqImplementation {

    @Autowired
    private AmqpTemplate amqTemplate;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RegistrationMailService registrationMailService;

    public void sendMessageToQueue(Email email)
    {
        final String exchange = "parkingLot.exchange";
        final String routingKey = "parkingLot.routingkey";
        amqTemplate.convertAndSend(exchange, routingKey,email);

    }

    @RabbitListener(queues = "${parkingLot.rabbitmq.queue}")
    public void send(Email email) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email.getEmailTo());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getMessage());
        javaMailSender.send(simpleMailMessage);
    }

    @RabbitListener(queues = "${parkingLot.rabbitmq.queue}")
    public void  receiveMessage(Email email)
    {
        registrationMailService.sendNotification(email);
    }
}
