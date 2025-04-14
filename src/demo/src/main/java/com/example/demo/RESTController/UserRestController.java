package com.example.demo.RESTController;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
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
        userDTOBasic = userService.createUserDTOBasic(userDTOBasic.username(), userDTOBasic.email(), userDTOBasic.password());

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(userDTOBasic.id()).toUri();

        return ResponseEntity.created(location).body(userDTOBasic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTOBasic> putUser(@PathVariable Long id, @RequestBody UserDTOBasic userDTOBasic) throws IOException {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con id: " + id));
        userService.editUserRest(userToUpdate, userDTOBasic.username(), userDTOBasic.email(), userDTOBasic.password());

        return ResponseEntity.ok(userMapper.toDTO(userToUpdate));
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