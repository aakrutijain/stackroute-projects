package com.stackroute.accountmanager.controller;

import com.stackroute.accountmanager.authentication.JWTGenerator;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.model.UserResponse;
import com.stackroute.accountmanager.service.AccountManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/accountmanager")
public class AccountManagerController {


    AccountManagerService accountManagerService;
    JWTGenerator jwtGenerator;

    @Autowired
    public AccountManagerController(AccountManagerService accountManagerService,JWTGenerator jwtGenerator){
        this.accountManagerService=accountManagerService;
        this.jwtGenerator=jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        User registeredUser= accountManagerService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws UserNotFoundException {
        Map<String,String> map=new HashMap<>();
        UserResponse registeredUser=accountManagerService.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        if(!registeredUser.getError().isEmpty()) {
            map.put("message", registeredUser.getError());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
        }
        if(registeredUser.getUser().getUsername().equals(user.getUsername())){
            map=jwtGenerator.generateToken(registeredUser.getUser());
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

}