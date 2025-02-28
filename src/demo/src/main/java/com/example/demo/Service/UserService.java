package com.example.demo.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User authenticateUser(String logger, String password) {
        if (logger.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]+)?$"))  { //If the logger has an email format, then check for it in the database by email
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

    public User registerUser(String username, String email, String password, boolean[] errorUserMail) throws IOException {
        if (userRepository.findByEmail(email) != null | userRepository.findByUsername(username) != null) {
            if (userRepository.findByEmail(email) != null) {
                errorUserMail[1] = true;
            }
            if (userRepository.findByUsername(username) != null) {
                errorUserMail[0] = true;
            }
            return null;
        }
        Path imagePath = Paths.get("src\\demo\\src\\main\\resources\\static\\assets\\img\\default-user-profile-image.webp");
        byte[] imageData = Files.readAllBytes(imagePath);
        String imageName = imagePath.getFileName().toString();
        User user = new User(username, password, email, new java.util.Date(), imageName, imageData);
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
    public boolean usernamePresent(String username) {
        return userRepository.findByUsername(username) != null;
    }
    public boolean emailPresent(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public ResponseEntity<byte[]> getUserImage(Long userId) {
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        byte[] imageData = user.getImageData();
        if (imageData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/*");
        return new ResponseEntity<byte[]>(imageData, headers, HttpStatus.OK);
    }
}
