package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import com.example.demo.model.Community;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private PostService postService;

    @GetMapping("/communities/{id}")
    public String showCommunity(HttpSession session, Model model, @PathVariable Long id) {
        User user = (User) session.getAttribute("user");
        Community community = communityService.findById(id);
        model.addAttribute("community", community);
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);
        model.addAttribute("posts", postService.findByCommunityIdOrderByCreationDateDesc(id));
        return "community";
    }

    @PostMapping("/community/create")
    public String createCommunity(@RequestParam String name) {
        communityService.createCommunity(name);
        return "redirect:/communities";
    }

    @PostMapping("/community/delete/{communityId}")
    public String deleteCommunity(@PathVariable Long communityId) {
        communityService.deleteById(communityId);
        return "redirect:/communities";
    }

}
