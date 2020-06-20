package com.stackroute.accountmanager.service;

import com.stackroute.accountmanager.config.RabbitMQMessageProducer;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.model.UserDTO;
import com.stackroute.accountmanager.model.UserResponse;
import com.stackroute.accountmanager.repository.AccountManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountManagerServiceImpl implements AccountManagerService {

    AccountManagerRepository accountManagerRepository;
    RabbitMQMessageProducer producer;

    @Autowired
    public AccountManagerServiceImpl(AccountManagerRepository accountManagerRepository, RabbitMQMessageProducer producer){
        this.accountManagerRepository=accountManagerRepository;
        this.producer=producer;
    }

    @Override
    public User saveUser(User user) {
        User savedUser=accountManagerRepository.save(user);
        UserDTO userDTO=new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
//        producer.sendMessage2UserQueue(userDTO);
        return savedUser;

    }

    @Override
    public UserResponse findByUsernameAndPassword(String userName, String password) throws UserNotFoundException {
        UserResponse userResponse = new UserResponse();
        User user=accountManagerRepository.findByUsernameAndPassword(userName,password);
        if(user!=null) {
            userResponse.setUser(user);
            userResponse.setError("");
        } else {
            userResponse.setError("Invalid Username/Password");
        }
        return userResponse;
    }
}