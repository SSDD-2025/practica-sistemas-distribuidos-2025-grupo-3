package com.example.demo.RESTController;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.DTO.user.UserDTOBasic;
import com.example.demo.DTO.user.UserMapper;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.model.User;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTOBasic> deleteUser(@PathVariable Long id) {

        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con id: " + id));

        UserDTOBasic deletedUserDTO = userMapper.toDTO(userToDelete);

        userService.deleteUser(id);

        return ResponseEntity.ok(deletedUserDTO);
    }

}