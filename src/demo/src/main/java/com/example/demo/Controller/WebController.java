package com.example.demo.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.Community.CommunityDTOBasic;
import com.example.demo.DTO.Post.PostDTO;
import com.example.demo.DTO.user.FollowedUserDTO;
import com.example.demo.DTO.user.FollowingUserDTO;
import com.example.demo.DTO.user.UserDTOBasic;

import com.example.demo.Service.CommentService;
import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class WebController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            UserDTOBasic userDTOBasic = userService.getUserByUsername(principal.getName());
            model.addAttribute("user", userDTOBasic);
            model.addAttribute("isGuest", false);
        } else {
            model.addAttribute("isGuest", true);
        }
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {

        List<PostDTO> latestPosts;
        latestPosts = postService.findTop5ByOrderByCreationDateDesc();

        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("isHome", true);

        return "home";
    }

    @GetMapping("/communities")
    public String communities(Model model) {

        model.addAttribute("comunities", communityService.findAll());
        model.addAttribute("isCommunities", true);

        return "communities";
    }

    @GetMapping("/posts")
    public String posts(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("actualPage", page);
        model.addAttribute("isPosts", true);
        return "posts";
    }

    @GetMapping("/followed")
    public String friendsPage(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(principal.getName());
        List<FollowedUserDTO> followedUsers = userService.getFollowedUsersWithPosts(userDTOBasic.id());
        model.addAttribute("followedUsers", followedUsers);
        model.addAttribute("isFriends", true);
        return "followed";
    }

    @GetMapping("/people")
    public String peoplePage(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(principal.getName());
        List<FollowingUserDTO> userList = userService.getAllUsersExceptCurrentUser(userDTOBasic);
        model.addAttribute("isPeople", true);
        model.addAttribute("users", userList);
        return "people";
    }

    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        UserDTOBasic otherUser = userService.getUserById(id);
        List<PostDTO> userPosts = postService.findByUserNameOrderByCreationDateDesc(otherUser);
        model.addAttribute("user", otherUser);
        model.addAttribute("posts", userPosts);
        return "profile";
    }

    @GetMapping("/userMainPage")
    public String userMainPage(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(principal.getName());
        model.addAttribute("posts", postService.findByUserNameOrderByCreationDateDesc(userDTOBasic));
        model.addAttribute("comments", commentService.findByUserName(userDTOBasic));
        return "userMainPage";
    }

    @GetMapping("/whoAreWe")
    public String who(Model model, HttpServletRequest request) {
        return "whoAreWe";
    }

    @GetMapping("/guest")
    public String guestAccess(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/home";
    }

    @GetMapping("/registrationPage")
    public String register() {
        return "registrationPage";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, HttpServletRequest request, @RequestParam(required = false) String mensaje) {
        Principal principal = request.getUserPrincipal();
        UserDTOBasic currentUser = userService.getUserByUsername(principal.getName());
        List<FollowingUserDTO> userList = userService.getAllUsersExceptCurrentUser(currentUser);
        List<CommunityDTOBasic> communities = communityService.findAll();

        model.addAttribute("usersLeft", userList.isEmpty());
        model.addAttribute("users", userList);
        model.addAttribute("communitiesLeft", communities.isEmpty());
        model.addAttribute("communities", communities);
        model.addAttribute("isAdminPage", true);

        if (mensaje != null && !mensaje.isEmpty()) {
            model.addAttribute("mensaje", mensaje);
        }

        return "adminPage";
    }

}
