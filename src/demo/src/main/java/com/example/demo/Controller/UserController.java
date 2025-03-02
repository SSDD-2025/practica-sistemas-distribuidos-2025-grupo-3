package com.example.demo.Controller;

import java.io.IOException;

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

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(String username, String email, String password,
            HttpSession session,
            Model model) throws IOException {
        boolean[] errorUserMail = new boolean[] { false, false };
        User user = userService.registerUser(username, email, password, errorUserMail);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/home";
        }
        model.addAttribute("errorUser", errorUserMail[0]);
        model.addAttribute("errorMail", errorUserMail[1]);
        return "index";
    }

    @PostMapping("/edit_user")
    public String editUser(@RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            HttpSession session,
            Model model) {
        User user = (User) session.getAttribute("user");
        if (username != null && !username.trim().isEmpty()) {
            if (!userService.usernamePresent(username)) {
                user.setUsername(username);
            }
        }
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password);
        }
        if (email != null && !email.trim().isEmpty()) {
            if (!userService.emailPresent(email)) {
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
        userService.updateUser(user);
        session.setAttribute("user", user);
        return "redirect:/user_main_page";

    }

    @PostMapping("/login")
    public String login(
            String logger,
            String password,
            HttpSession session,
            Model model) {
        User user = userService.authenticateUser(logger, password);

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/home";
        }
        model.addAttribute("error", true);
        return "index";
    }

    @GetMapping("/guest")
    public String guestAccess(HttpSession session) {
        User guestUser = userService.getGuestUser();
        session.setAttribute("user", guestUser);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/user/delete")
    public String deleteUser(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            userService.deleteUser(user.getId());
            session.invalidate();
        }

        return "redirect:/";
    }

    @PostMapping("/follow/{id}")
    public String toggleFollow(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        userService.toggleFollow(currentUser.getId(), id);
        return "redirect:/people";
    }

    @GetMapping("/user/image/{userId}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long userId) {
        return userService.getUserImage(userId);
    }
}
