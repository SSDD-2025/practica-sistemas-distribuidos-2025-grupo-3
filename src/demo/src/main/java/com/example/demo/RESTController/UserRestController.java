package com.example.demo.RESTController;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.DTO.user.UserDTOBasic;
import com.example.demo.DTO.user.UserMapper;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public Collection<UserDTOBasic> getAllUsers() {
        return userService.findAllDTOBasic();
    }

    @PostMapping("/")
    public ResponseEntity<UserDTOBasic> postUser(@RequestBody UserDTOBasic userDTOBasic) {
        userDTOBasic = userService.createUserDTOBasic(userDTOBasic.username(), userDTOBasic.email(),
                userDTOBasic.password());

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(userDTOBasic.id()).toUri();

        return ResponseEntity.created(location).body(userDTOBasic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTOBasic> putUser(@PathVariable Long id, @RequestBody UserDTOBasic userDTOBasic,
            HttpServletRequest request) throws IOException {
        Principal principal = request.getUserPrincipal();

        if (!userDTOBasic.id().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have the permission.");
        }

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found by id " + id));

        userService.editUserRest(userToUpdate, userToUpdate.getUsername(), userDTOBasic.email(),
                userDTOBasic.password());

        return ResponseEntity.ok(userMapper.toDTO(userToUpdate));
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Object> putUserImage(
            @PathVariable long id, @RequestParam MultipartFile imageFile, HttpServletRequest request)
            throws IOException {

        Principal principal = request.getUserPrincipal();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(principal.getName());

        if (userDTOBasic.id() != id) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "you can only edit your own profile image.");
        }

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found by id " + id));

        userService.editUserProfileImage(userToUpdate, imageFile);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTOBasic> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(principal.getName());

        if (!userDTOBasic.id().equals(id) && !userService.isAdmin(userDTOBasic)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have the permission.");
        }

        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found by id: " + id));

        UserDTOBasic deletedUserDTO = userMapper.toDTO(userToDelete);

        userService.deleteUser(id);

        return ResponseEntity.ok(deletedUserDTO);
    }

}