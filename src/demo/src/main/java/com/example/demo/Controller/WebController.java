package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.DTO.FollowedUserDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Repository.CommunityRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Service.UserService;
import com.example.demo.model.Post;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired 
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("error", false);
        return "index";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);

        return "home"; 
    }

    @GetMapping("/communities")
    public String communities(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);
        model.addAttribute("comunities", communityRepository.findAll());
        model.addAttribute("isCommunities", true);
        return "communities"; 
    }

    @GetMapping("/followed")
    public String friendsPage(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        List<FollowedUserDTO> followedUsers = userService.getFollowedUsersWithPosts(currentUser.getId());
        model.addAttribute("followedUsers", followedUsers);
        model.addAttribute("isFriends", true);
        return "followed";
    }
        
    @GetMapping("/people")
    public String peoplePage(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            return "redirect:/login"; // Redirige si no hay sesión activa
        }

        List<UserDTO> userList = userService.getAllUsers().stream()
            .filter(user -> !user.getId().equals(currentUser.getId())) // No mostrar al usuario actual
            .filter(user -> user.getId() != 1) // No mostrar al usuario invitado
            .map(user -> new UserDTO(user.getId(), user.getUsername(), userService.isFollowing(currentUser, user)))
            .collect(Collectors.toList());

        model.addAttribute("isPeople", true);
        model.addAttribute("users", userList);
        return "people";
    }


    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/people"; // Si no existe, redirigir a otra página
        }

        List<Post> userPosts = postRepository.findByUserNameOrderByCreationDateDesc(user);

        model.addAttribute("user", user);
        model.addAttribute("posts", userPosts);
        
        return "profile";
    }


    @GetMapping("/user_main_page")
    public String login(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);
        model.addAttribute("posts", postRepository.findByUserNameOrderByCreationDateDesc(user));
        return "user_main_page";
    }

    @GetMapping("/quienes_somos")
    public String who(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);
        return "quienes_somos";
    }

    @GetMapping("/registration_page")
    public String register() {
        return "registration_page";
    }
    @GetMapping("/edit_user_page")
    public String editUser() {
        return "edit_user_page";
    }

}
