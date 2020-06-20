package com.stackroute.accountmanager.service;

import com.stackroute.accountmanager.config.RabbitMQMessageProducer;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.model.UserResponse;
import com.stackroute.accountmanager.repository.AccountManagerRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class AccountManagerServiceTest {

    @Mock
    AccountManagerRepository accountManagerRepository;
    @Mock
    RabbitMQMessageProducer producer;
    User user;
    UserResponse userResponse;

    @InjectMocks
    AccountManagerServiceImpl accountManagerService;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        user=new User();
        user.setUsername("abc");
        user.setPassword("xyz");
        user.setEmail("abc@xyz.com");

        userResponse = new UserResponse();
        userResponse.setUser(user);
        userResponse.setError("");
    }

    @After
    public void tearDown(){
        user=null;
    }

    @Test
    public void registerUserTest(){
        when(accountManagerRepository.save(user)).thenReturn(user);
        User response=accountManagerService.saveUser(user);
        Assert.assertEquals(response.getUsername(),user.getUsername());
        verify(accountManagerRepository,times(1)).save(user);
    }

    @Test
    public void loginSuccessTest() throws UserNotFoundException {
        when(accountManagerRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword())).thenReturn(user);
        UserResponse savedUser=accountManagerService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        Assert.assertEquals(user.getUsername(),savedUser.getUser().getUsername());
        verify(accountManagerRepository,times(1)).findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}