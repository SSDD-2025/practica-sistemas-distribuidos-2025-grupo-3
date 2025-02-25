package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;

import org.springframework.ui.Model;

import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        User user = userService.authenticateUser(email, password);
        
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
}
