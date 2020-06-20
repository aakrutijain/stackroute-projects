package com.stackroute.accountmanager.repository;

import com.stackroute.accountmanager.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class AccountManagerRepositoryTest {

    @Autowired
    private AccountManagerRepository accountManagerRepository;
    User user;

    @Before
    public void setup(){
        user=new User();
        user.setUsername("abc");
        user.setPassword("xyz");
        user.setEmail("abc@xyz.com");
    }

    @After
    public void tearDown(){
        user=null;
        accountManagerRepository.deleteAll();
    }


    @Test
    public void registerUserTest(){
        accountManagerRepository.save(user);
        User fetchedUser=accountManagerRepository.findById(user.getId()).get();
        Assert.assertEquals(user.getUsername(),fetchedUser.getUsername());
        accountManagerRepository.deleteAll();
    }

    @Test
    public void loginSuccessTest(){
        accountManagerRepository.save(user);
        User userObj=accountManagerRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        Assert.assertEquals(user.getUsername(),userObj.getUsername());
        accountManagerRepository.delete(user);
    }
}