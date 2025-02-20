package com.example.demo.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class UserService {
    private Map<Long, User> users = new HashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public UserService(){}

    public Collection<User> findAll(){
        return users.values();
    }

    public User findById(Long id){
        return users.get(id);
    }

    public void deleteById(Long id){
        users.remove(id);
    }

    public void save(User u){
        Long id = nextId.incrementAndGet();
        u.setId(id);
        users.put(id, u);
    }
    
}
