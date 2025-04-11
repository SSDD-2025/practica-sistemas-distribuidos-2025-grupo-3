package com.example.demo.Data;

import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.User;
import com.example.demo.model.User.Role;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseUsersLoader {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initDatabase() throws Exception {
        
        ClassPathResource imgFile;

        /* USERS */
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview1.webp");
        userRepository.save(new User("Juan Pérez", passwordEncoder.encode("password1"), "juan.perez@example.com", new java.util.Date(),
                imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath()), List.of(Role.ROLE_USER)));
        imgFile = new ClassPathResource("static/assets/img/default-user-profile-image.webp");
        userRepository.save(new User("María García", passwordEncoder.encode("password2"),  "maria.garcia@example.com", new java.util.Date(),
                imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath()), List.of(Role.ROLE_USER)));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview2.webp");
        userRepository.save(new User("Carlos Rodríguez", passwordEncoder.encode("password3"),  "carlos.rodriguez@example.com",
                new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath()), List.of(Role.ROLE_USER)));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview3.webp");
        userRepository.save(new User("Ana Martínez", passwordEncoder.encode("password4"), "ana.martinez@example.com", new java.util.Date(),
                imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath()), List.of(Role.ROLE_USER)));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview4.webp");
        userRepository.save(new User("Luis Hernández", passwordEncoder.encode("password5"), "luis.hernandez@example.com", new java.util.Date(),
                imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath()), List.of(Role.ROLE_USER)));
        imgFile = new ClassPathResource("static/assets/img/default-user-profile-image.webp");
        userRepository.save(new User("Laura López", passwordEncoder.encode("password6"), "laura.lopez@example.com", new java.util.Date(),
                imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath()), List.of(Role.ROLE_USER)));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview5.webp");
        userRepository.save(new User("José González", passwordEncoder.encode("password7"), "jose.gonzalez@example.com", new java.util.Date(),
                imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath()), List.of(Role.ROLE_USER)));
        imgFile = new ClassPathResource("static/assets/img/default-user-profile-image.webp");
        userRepository.save(new User("Marta Sánchez", passwordEncoder.encode("password8"), "marta.sanchez@example.com", new java.util.Date(),
                imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath()), List.of(Role.ROLE_USER)));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview6.webp");
        userRepository.save(new User("David Fernández", passwordEncoder.encode("password9"), "david.fernandez@example.com",
                new java.util.Date(), imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath()), List.of(Role.ROLE_USER)));
        imgFile = new ClassPathResource("static/assets/img/profilePictures/preview7.webp");
        userRepository.save(new User("Elena Ruiz", passwordEncoder.encode("password10"), "elena.ruiz@example.com", new java.util.Date(),
                imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath()), List.of(Role.ROLE_USER)));
        /* Developer users */
        imgFile = new ClassPathResource("static/assets/img/admin.jpg");
        userRepository.save(new User("Sergio", passwordEncoder.encode("454548"), "s.espinosa.2020@alumnos.urjc.es", new java.util.Date(),
                imgFile.getFilename(), Files.readAllBytes(imgFile.getFile().toPath()), List.of(Role.ROLE_USER, Role.ROLE_ADMIN)));
    }
}
