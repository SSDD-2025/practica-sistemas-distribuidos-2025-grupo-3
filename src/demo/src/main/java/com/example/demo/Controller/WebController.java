package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.DTO.user.FollowedUserDTO;
import com.example.demo.DTO.user.UserDTO;
import com.example.demo.Service.CommentService;
import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.model.User.Role;
import com.example.demo.security.CustomUserDetails;

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

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());   
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Fetch user details from the current authentication principal
        User user = userService.getUserByUsername(userDetails.getUsername());
        
        // Default to fetching the latest posts ordered by creation date
        List<Post> latestPosts;
        latestPosts = postService.findTop5ByOrderByCreationDateDesc();
    
        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getRoles().contains(Role.ROLE_GUEST));
        model.addAttribute("isHome", true);
        
        return "home";
    }
    

    @GetMapping("/communities")
    public String communities(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getRoles().contains(Role.ROLE_GUEST));
        model.addAttribute("comunities", communityService.findAll());
        model.addAttribute("isCommunities", true);
        return "communities";
    }

    @GetMapping("/followed")
    public String friendsPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        List<FollowedUserDTO> followedUsers = userService.getFollowedUsersWithPosts(user.getId());
        model.addAttribute("followedUsers", followedUsers);
        model.addAttribute("isFriends", true);
        return "followed";
    }

    @GetMapping("/people")
    public String peoplePage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        List<UserDTO> userList = userService.getAllUsers().stream()
                .filter(user -> !user.getId().equals(currentUser.getId()))
                .filter(user -> user.getId() != 1)
                .map(user -> new UserDTO(user.getId(), user.getUsername(), userService.isFollowing(currentUser, user)))
                .collect(Collectors.toList());
        model.addAttribute("isPeople", true);
        model.addAttribute("users", userList);
        return "people";
    }

    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Long id, Model model, HttpServletRequest request) {
        User otherUser = userService.getUserById(id);
        List<Post> userPosts = postService.findByUserNameOrderByCreationDateDesc(otherUser);
        model.addAttribute("user", otherUser);
        model.addAttribute("posts", userPosts);
        return "profile";
    }

    @GetMapping("/user_main_page")
    public String userMainPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getRoles().contains(Role.ROLE_GUEST));
        model.addAttribute("posts", postService.findByUserNameOrderByCreationDateDesc(user));
        model.addAttribute("comments", commentService.findByUserName(user));
        return "user_main_page";
    }

    @GetMapping("/who_are_we")
    public String who(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getRoles().contains(Role.ROLE_GUEST));
        return "who_are_we";
    }

    @GetMapping("/registration_page")
    public String register() {
        return "registration_page";
    }

    @GetMapping("/edit_user_page")
    public String editUser(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "edit_user_page";
    }
}



