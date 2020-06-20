package com.stackroute.giphermanager.config;

import com.stackroute.giphermanager.model.UserDTO;
import com.stackroute.giphermanager.service.GifManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RabbitMQMessageConsumer {

    @Autowired
    GifManagerService gifManagerService;

    @RabbitListener(queues="user_queue")
    public void registerUser(UserDTO userDTO){
        UserDTO gipherUser=new UserDTO();
        gipherUser.setUsername(userDTO.getUsername());
        gipherUser.setEmail(userDTO.getEmail());
        gipherUser.setBookmarkDTOList(new ArrayList<>());
        gifManagerService.createNewUser(gipherUser);
    }
}