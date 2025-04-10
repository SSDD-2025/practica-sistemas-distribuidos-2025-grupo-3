package com.example.demo.RESTController;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.user.UserDTOBasic;
import com.example.demo.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public Collection<UserDTOBasic> getAllUsers() {
        return userService.findAllDTOBasic();
    }
}