package com.example.demo.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.CommunityRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Community;
import com.example.demo.model.User;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
    
    @Autowired
    private CommunityRepository cR;
    
    @Autowired
    private UserRepository uR;


    @PostConstruct
    public void run() throws Exception{

        cR.save(new Community("Java"));
        cR.save(new Community("Futbol"));
        cR.save(new Community("Baloncesto"));

        uR.save(new User("username", "password", "email", null));
        uR.save(new User("username2", "password2", "email2", null));

    }
}
