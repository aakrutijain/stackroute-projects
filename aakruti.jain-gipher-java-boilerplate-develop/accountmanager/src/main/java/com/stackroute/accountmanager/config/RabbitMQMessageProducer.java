package com.stackroute.accountmanager.config;

import com.stackroute.accountmanager.model.UserDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessageProducer {

    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    @Autowired
    RabbitMQMessageProducer(RabbitTemplate rabbitTemplate,DirectExchange directExchange){
        this.rabbitTemplate=rabbitTemplate;
        this.directExchange=directExchange;
    }

    public void sendMessage2UserQueue(UserDTO gipherUserDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),"user_routing",gipherUserDTO);
    }
}