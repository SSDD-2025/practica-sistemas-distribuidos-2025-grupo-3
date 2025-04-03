package com.example.demo.Controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.User;

import org.springframework.ui.Model;

import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(String username, String email, String password, HttpServletRequest request, Model model) throws IOException {
        boolean[] registrationStatus = new boolean[] { false, false, false };

        userService.registerUser(username, email, password, registrationStatus);

        model.addAttribute("errorUser", registrationStatus[0]);
        model.addAttribute("errorMail", registrationStatus[1]);
        model.addAttribute("registrationSuccess", registrationStatus[2]);

        return "index";
    }

    @GetMapping("/editUserPage")
    public String editUser(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "editUserPage";
    }


    @PostMapping("/editUser")
    public String editUser(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            HttpServletRequest request,
            Model model) throws IOException {

        User user = userService.getUserByUsername(request.getUserPrincipal().getName());

        userService.editUser(user, email, password, imageFile);

        return "redirect:/userMainPage";

    }


    @PostMapping("/user/delete")
    public String deleteUser(HttpServletRequest request) {
        User user = userService.getUserByUsername(request.getUserPrincipal().getName());
        userService.deleteUser(user.getId());

        return "redirect:/";
    }

    @PostMapping("/follow/{id}")
    public String toggleFollow(@PathVariable Long id, HttpServletRequest request) {
        
        String name = request.getUserPrincipal().getName();
        User user = userService.getUserByUsername(name);

        userService.toggleFollow(user.getId(), id);
        return "redirect:/people";
    }

    @GetMapping("/user/image/{userId}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long userId) {
        return userService.getUserImage(userId);
    }
}
