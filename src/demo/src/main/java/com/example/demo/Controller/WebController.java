package com.example.demo.Controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.DTO.user.FollowedUserDTO;
import com.example.demo.DTO.user.UserDTO;
import com.example.demo.Service.CommentService;
import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;
import com.example.demo.model.Post;
import com.example.demo.model.User;

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

		if(principal != null) {

            User user = userService.getUserByUsername(principal.getName());
            model.addAttribute("user", user);
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

        List<Post> latestPosts;
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

    @GetMapping("/followed")
    public String friendsPage(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        User user = userService.getUserByUsername(principal.getName());
        List<FollowedUserDTO> followedUsers = userService.getFollowedUsersWithPosts(user.getId());
        model.addAttribute("followedUsers", followedUsers);
        model.addAttribute("isFriends", true);
        return "followed";
    }

    @GetMapping("/people")
    public String peoplePage(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User currentUser = userService.getUserByUsername(principal.getName());
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
    public String userProfile(@PathVariable Long id, Model model) {
        User otherUser = userService.getUserById(id);
        List<Post> userPosts = postService.findByUserNameOrderByCreationDateDesc(otherUser);
        model.addAttribute("user", otherUser);
        model.addAttribute("posts", userPosts);
        return "profile";
    }

    @GetMapping("/user_main_page")
    public String userMainPage(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("posts", postService.findByUserNameOrderByCreationDateDesc(user));
        model.addAttribute("comments", commentService.findByUserName(user));
        return "user_main_page";
    }

    @GetMapping("/who_are_we")
    public String who(Model model, HttpServletRequest request) {
        return "who_are_we";
    }

    @GetMapping("/guest")
    public String guestAccess(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/home";
    }

    @GetMapping("/registration_page")
    public String register() {
        return "registration_page";
    }

}



