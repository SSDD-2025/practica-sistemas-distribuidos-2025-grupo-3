package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Service.CommunityService;


@Controller
public class WebController {

    @Autowired
    private CommunityService cs;


    @GetMapping("/home")
    public String showHall(Model model) {
        model.addAttribute("comunities", cs.findAll());
        return "home";
    }

    @GetMapping("/communities/{id}")
    public String showCommunity(Model model, @PathVariable Long id) {
        model.addAttribute("community", cs.findById(id));
        model.addAttribute("id", id);
        return "community";
    }
    

    @GetMapping("/user_main_page")
    public String login() {
        return "user_main_page";
    }

}
