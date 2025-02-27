package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
            Model model) {
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
            if (userService.usernamePresent(username)) {
                user.setUsername(username);
            }
        }
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password);
        }
        if (email != null && !email.trim().isEmpty()) {
            if (userService.emailPresent(email)) {
                user.setEmail(username);
            }
        }
        if (imageFile != null) {
            user.setImage(imageFile.getOriginalFilename());
            try {
                user.setImageData(imageFile.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            userService.deleteUser(user.getId()); // Llama al método deleteUser en UserService
            session.invalidate(); // Invalida la sesión después de eliminar al usuario
        }

        return "redirect:/"; // Redirige a la página principal después de eliminar al usuario
    }
}
