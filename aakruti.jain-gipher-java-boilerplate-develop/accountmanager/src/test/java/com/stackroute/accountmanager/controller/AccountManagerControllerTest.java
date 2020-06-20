package com.stackroute.accountmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.accountmanager.authentication.JWTGenerator;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.model.UserResponse;
import com.stackroute.accountmanager.service.AccountManagerService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountManagerController.class)
public class AccountManagerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AccountManagerService accountManagerService;
    @MockBean
    JWTGenerator jwtGenerator;
    @InjectMocks
    AccountManagerController accountManagerController;
    User user;
    UserResponse userResponse;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(accountManagerController).build();
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
    public void registerUserTest() throws Exception{
        when(accountManagerService.saveUser(user)).thenReturn(user);
        ResponseEntity<?> responseEntity = accountManagerController.registerUser(user);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(responseEntity.getBody(), user);
    }

    @Test
    public void loginUserTest() throws Exception{
        when(accountManagerService.findByUsernameAndPassword(user.getUsername(),user.getPassword())).thenReturn(userResponse);
        when(jwtGenerator.generateToken(user)).thenReturn(generateToken(user));
        ResponseEntity<?> responseEntity = accountManagerController.loginUser(user);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        Map<String, String> response = (Map<String, String>) responseEntity.getBody();
        Assert.assertEquals(response.get("message"), generateToken(user).get("message"));
    }

    private static String toJson(User gipherUser){
        String result=null;
        try {
            final ObjectMapper objectMapper=new ObjectMapper();
            final String json=objectMapper.writeValueAsString(gipherUser);
            result=json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Map<String, String> generateToken(User user) {
        String jwtToken= Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"s9cr3at_k3y")
                .compact();

        Map<String,String> map=new HashMap<>();
        map.put("token",jwtToken);
        map.put("message","User logged in ..");
        return map;
    }

}