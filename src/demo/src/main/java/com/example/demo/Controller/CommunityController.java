package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Repository.CommunityRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.model.Community;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommunityController {

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/communities/{id}")
    public String showCommunity(HttpSession session, Model model, @PathVariable Long id) {
        User user = (User) session.getAttribute("user");
        communityRepository.findById(id).ifPresent(community -> model.addAttribute("community", community));
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);
        model.addAttribute("posts", postRepository.findByCommunityId(id));
        return "community";
    }

    @PostMapping("/community/create")
    public String createCommunity(
            @RequestParam String name, // Asegúrate de que el parámetro se obtenga correctamente
            HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        if (name == null || name.isEmpty()) {
            // Manejar el caso donde el nombre es inválido (opcional)
            return "redirect:/home"; // O redirigir a alguna página de error
        }

        Community community = new Community(name); // Aquí es donde debes crear la comunidad
        communityRepository.save(community);

        return "redirect:/home"; // Redirige a la página principal tras la creación
    }

    @PostMapping("/community/delete/{communityId}")
    public String deleteCommunity(@PathVariable Long communityId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        communityRepository.deleteById(communityId);

        return "redirect:/home";
    }

}
