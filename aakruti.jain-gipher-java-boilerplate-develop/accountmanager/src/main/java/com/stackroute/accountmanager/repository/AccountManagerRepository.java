package com.stackroute.accountmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stackroute.accountmanager.model.User;

public interface AccountManagerRepository extends JpaRepository<User,Integer> {
    public User findByUsernameAndPassword(String username, String password);
}