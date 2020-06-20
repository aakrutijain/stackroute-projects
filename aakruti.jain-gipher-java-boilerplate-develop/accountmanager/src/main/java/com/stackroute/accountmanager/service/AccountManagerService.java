package com.stackroute.accountmanager.service;

import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.model.UserResponse;

public interface AccountManagerService {
    public User saveUser(User user);
    public UserResponse findByUsernameAndPassword(String userName, String passsword) throws UserNotFoundException;

}