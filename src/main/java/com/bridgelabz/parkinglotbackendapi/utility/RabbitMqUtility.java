package com.bridgelabz.parkinglotbackendapi.utility;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class RabbitMqUtility {


    @Component
    public class QueueProducer {

        @Value("${direct.exchange}")
        private String DirectExchange;

        private final RabbitTemplate rabbitTemplate;

        @Autowired
        public QueueProducer(RabbitTemplate rabbitTemplate) {
            super();
            this.rabbitTemplate = rabbitTemplate;
        }
    }
}
