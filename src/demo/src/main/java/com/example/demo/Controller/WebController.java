package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Repository.CommunityRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired 
    private PostRepository postRepository;

    /*@GetMapping("/home")
    public String showHall(Model model) {
        model.addAttribute("comunities", communityRepository.findAll());
        return "home";
    }*/

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
        model.addAttribute("comunities", communityRepository.findAll());

        return "home"; 
    }

    @GetMapping("/communities/{id}")
    public String showCommunity(HttpSession session, Model model, @PathVariable Long id) {
        User user = (User) session.getAttribute("user");
        communityRepository.findById(id).ifPresent(community -> model.addAttribute("community", community));
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);
        model.addAttribute("posts", postRepository.findByCommunityId(id));
        return "community";
    }
    
    @GetMapping("/user_main_page")
    public String login(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);
        return "user_main_page";
    }

    @GetMapping("/registration_page")
    public String register() {
        return "registration_page";
    }

}
