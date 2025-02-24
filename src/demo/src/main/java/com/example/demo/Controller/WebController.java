package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Repository.CommunityRepository;

@Controller
public class WebController {

    @Autowired
    private CommunityRepository communityRepository;

    @GetMapping("/home")
    public String showHall(Model model) {
        model.addAttribute("comunities", communityRepository.findAll());
        return "home";
    }

    @GetMapping("/communities/{id}")
    public String showCommunity(Model model, @PathVariable Long id) {
        communityRepository.findById(id).ifPresent(community -> model.addAttribute("community", community));
        return "community";
    }
    
    @GetMapping("/user_main_page")
    public String login() {
        return "user_main_page";
    }

    @GetMapping("/registration_page")
    public String register() {
        return "registration_page";
    }

}
