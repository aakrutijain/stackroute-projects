package com.stackroute.giphermanager.config;

import com.stackroute.giphermanager.model.BookmarkDTO;
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

    public void sendMessageToReccomendationQueue(BookmarkDTO bookmarkDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),"recommendations_routing",bookmarkDTO);
    }
}