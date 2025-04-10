package com.example.demo.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.Community.CommunityDTOBasic;
import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;
import com.example.demo.model.User;
import com.example.demo.model.User.Role;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {

            User user = userService.getUserByUsername(principal.getName());
            boolean isAdmin = user.getRoles().contains(Role.ROLE_ADMIN);
            model.addAttribute("user", user);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("isGuest", false);
        } else {
            model.addAttribute("isAdmin", false);
            model.addAttribute("isGuest", true);
        }
    }

    @GetMapping("/communities/{id}")
    public String showCommunity(Model model, @PathVariable Long id) {

        CommunityDTOBasic communityDTOBasic = communityService.findDTOBasicById(id);
        model.addAttribute("community", communityDTOBasic);
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

        return "redirect:/admin?mensaje=Comunidad eliminada correctamente";
    }

}
