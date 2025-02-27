package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User authenticateUser(String logger, String password) {
        if (logger.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+$")) { //If the logger has an email format, then check for it in the database by email
            User user = userRepository.findByEmail(logger);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
        }else { //If the logger does not have an email format, then check for it in the database by username
            User user = userRepository.findByUsername(logger);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getGuestUser() {
        return userRepository.findById(1L).orElse(null);
    }

    public User registerUser(String username, String email, String password, boolean[] errorUserMail) {
        if (userRepository.findByEmail(email) != null | userRepository.findByUsername(username) != null) {
            if (userRepository.findByEmail(email) != null) {
                errorUserMail[1] = true;
            }
            if (userRepository.findByUsername(username) != null) {
                errorUserMail[0] = true;
            }
            return null;
        }
        User user = new User(username, password, email, new java.util.Date());
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
