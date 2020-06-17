package com.bridgelabz.parkinglotbackendapi.configuration;



import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfigurartion {

    @Value("${parkingLot.rabbitmq.queue}")
    private String queueName;

    @Value("${parkingLot.rabbitmq.exchange}")
    private String exchange;

    @Value("${parkingLot.rabbitmq.routingkey}")
    private String routingKey;

    @Bean
    Queue queue() {

        return new Queue(queueName);
    }

    @Bean
    DirectExchange exchange() {

        return new DirectExchange(exchange);
    }

       @Bean
       Binding binding(Queue queue, DirectExchange exchange) {
	      return BindingBuilder.bind(queue).to(exchange).with(routingKey);
}

    @Bean
    public MessageConverter jsonMessageConverter() {

        return new Jackson2JsonMessageConverter();
    }
}
