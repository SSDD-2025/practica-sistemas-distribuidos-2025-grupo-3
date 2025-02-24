package com.example.demo.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class UserService {
    private Map<String, User> users = new HashMap<>();

    public UserService(){}

    public Collection<User> findAll(){
        return users.values();
    }

    public User findById(String username){
        return users.get(username);
    }

    public void deleteById(String username){
        users.remove(username);
    }

    public void save(User u){
        users.put(u.getUsername(), u);
    }
    
}
