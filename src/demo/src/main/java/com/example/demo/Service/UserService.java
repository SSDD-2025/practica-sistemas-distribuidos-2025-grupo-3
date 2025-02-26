package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getGuestUser() {
        return userRepository.findById(1L).orElse(null);
    }

    public User registerUser(String username, String email, String password) {
        if (userRepository.findByEmail(email) != null | userRepository.findByUsername(username) != null) {
            return null;
        }
        User user = new User(username, password, email, new java.util.Date());
        return userRepository.save(user);
    }
}
