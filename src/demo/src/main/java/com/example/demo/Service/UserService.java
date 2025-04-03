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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.user.FollowedUserDTO;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.User;
import com.example.demo.model.User.Role;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public User registerUser(String username, String email, String password, boolean[] registrationStatus) throws IOException {
        boolean emailExists = userRepository.findByEmail(email).isPresent();
        boolean usernameExists = userRepository.findByUsername(username).isPresent();
    
        if (emailExists || usernameExists) {
            registrationStatus[1] = emailExists;
            registrationStatus[0] = usernameExists;
            return null;
        }
    
        ClassPathResource imgFile = new ClassPathResource("static/assets/img/default-user-profile-image.webp");
        byte[] imageData = Files.readAllBytes(imgFile.getFile().toPath());
        String imageName = imgFile.getFilename();
    
        User user = new User(username, passwordEncoder.encode(password), email, new java.util.Date(), imageName, imageData, List.of(Role.ROLE_USER));
        
        registrationStatus[2] = true;

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

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
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
                        friend.getImageData(),
                        postRepository.findTop3ByuserNameOrderByCreationDateDesc(friend) 
                ))
                .collect(Collectors.toList());
    }

    public void editUser(User user, String email, String password, MultipartFile imageFile)
            throws IOException {

        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if (email != null && !email.trim().isEmpty()) {
            if (!userRepository.findByEmail(email).isPresent()) {
                user.setEmail(email);
            }
        }
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                user.setImage(imageFile.getOriginalFilename());
                user.setImageData(imageFile.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing the image", e);
        }

        userRepository.save(user);
    }

}
