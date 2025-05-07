package com.example.demo.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.Community.CommunityDTOBasic;
import com.example.demo.DTO.Post.PostDTO;
import com.example.demo.DTO.user.UserDTOBasic;
import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;

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
            UserDTOBasic userDTOBasic = userService.getUserByUsername(principal.getName());
            boolean isAdmin = userService.isAdmin(userDTOBasic);
            model.addAttribute("user", userDTOBasic);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("isGuest", false);
        } else {
            model.addAttribute("isAdmin", false);
            model.addAttribute("isGuest", true);
        }
    }

    @GetMapping("/communities/{id}")
    public String showCommunity(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            Model model, @PathVariable Long id) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> postsPage = postService.findByCommunityIdWithPagination(id, pageable);
        CommunityDTOBasic communityDTOBasic = communityService.findDTOBasicById(id);

        model.addAttribute("community", communityDTOBasic);
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("postsBoolean", postsPage.hasContent());
        model.addAttribute("currentPage", postsPage.getNumber() + 1);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        model.addAttribute("previousPage", postsPage.hasPrevious() ? postsPage.getNumber() - 1 : 0);
        model.addAttribute("nextPage", postsPage.hasNext() ? postsPage.getNumber() + 1 : postsPage.getTotalPages() - 1);

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
