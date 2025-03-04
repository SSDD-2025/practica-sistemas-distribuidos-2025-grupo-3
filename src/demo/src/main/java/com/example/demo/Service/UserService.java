package com.example.demo.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.FollowedUserDTO;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.User;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public User authenticateUser(String logger, String password) {
        if (logger.matches("^[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\\.[a-zA-Z]+)*$"))  { //If the logger has an email format, then check for it in the database by email
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
        ClassPathResource imgFile = new ClassPathResource("static/assets/img/default-user-profile-image.webp");
        byte[] imageData = Files.readAllBytes(imgFile.getFile().toPath());
        String imageName = imgFile.getFilename();
        User user = new User(username, password, email, new java.util.Date(), imageName, imageData);
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void saveUser(User currentUser) {
        userRepository.save(currentUser);
    }

    public boolean isFollowing(User currentUser, User otherUser) {
        return userRepository.existsFriendship(currentUser.getId(), otherUser.getId());
    }
    

    @Transactional
    public void toggleFollow(Long userId, Long friendId) {
        User user = userRepository.findById(userId).orElseThrow();
        User friend = userRepository.findById(friendId).orElseThrow();
    
        if (user.getFriends().contains(friend)) {
            user.getFriends().remove(friend);
        } else {
            user.getFriends().add(friend);
        }
        userRepository.save(user);
    }

    public List<FollowedUserDTO> getFollowedUsersWithPosts(Long currentUserId) {
    User currentUser = userRepository.findByIdWithFriends(currentUserId);

    return currentUser.getFriends().stream()
        .map(friend -> new FollowedUserDTO(
            friend.getId(),
            friend.getUsername(),
            friend.getImageData(), // Asegúrate de tener este campo en tu entidad
            postRepository.findTop3ByuserNameOrderByCreationDateDesc(friend) // Últimos 3 posts
        ))
        .collect(Collectors.toList());
}

    public void editUser(User user, String username, String email, String password, MultipartFile imageFile) throws IOException {
        if (username != null && !username.trim().isEmpty()) {
            if (!(userRepository.findByUsername(username) != null)) {
                user.setUsername(username);
            }
        }
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password);
        }
        if (email != null && !email.trim().isEmpty()) {
            if (!(userRepository.findByEmail(email) != null)) {
                user.setEmail(email);
            }
        }
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                user.setImage(imageFile.getOriginalFilename());
                user.setImageData(imageFile.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar la imagen", e);
        }
        /*
         * We should later on add some custom error or success
         * messages based on what we were able to change or not
         */

        userRepository.save(user);
    }
    
}
