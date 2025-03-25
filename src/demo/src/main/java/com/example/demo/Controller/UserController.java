package com.example.demo.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/guest")
    public String guestAccess() {
        User guestUser = userService.getGuestUser();  // Obtiene un usuario "invitado"
    
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                guestUser.getUsername(), 
                null, 
                List.of(new SimpleGrantedAuthority("ROLE_GUEST"))  // Asigna rol invitado
            );
    
        SecurityContextHolder.getContext().setAuthentication(authToken);  // Guarda autenticación
    
        return "redirect:/home";  // Redirige a la página principal
    }
    

    @PostMapping("/register")
    public String register(String username, String email, String password, HttpServletRequest request, Model model) throws IOException {
        boolean[] errorUserMail = new boolean[] { false, false };
    
        request.getSession().invalidate();
    
        User user = userService.registerUser(username, email, password, errorUserMail);
        if (user != null) {
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), password, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    
            SecurityContextHolder.getContext().setAuthentication(authToken);
    
            return "redirect:/home";
        }
        model.addAttribute("errorUser", errorUserMail[0]);
        model.addAttribute("errorMail", errorUserMail[1]);
        return "index";
    }


    @PostMapping("/edit_user")
    public String editUser(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            HttpServletRequest request,
            Model model) throws IOException {

        User user = userService.getUserByUsername(request.getUserPrincipal().getName());

        userService.editUser(user, username, email, password, imageFile);

        return "redirect:/user_main_page";

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
