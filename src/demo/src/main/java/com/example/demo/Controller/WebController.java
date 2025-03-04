package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.DTO.FollowedUserDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Service.CommentService;
import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;
import com.example.demo.model.Post;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpSession;

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
        model.addAttribute("error", false);
        return "index";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Post> latestPosts = postService.findTop5ByOrderByCreationDateDesc();

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);
        model.addAttribute("isHome", true);
        return "home"; 
    }

    @GetMapping("/communities")
    public String communities(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);
        model.addAttribute("comunities", communityService.findAll());
        model.addAttribute("isCommunities", true);
        return "communities"; 
    }

    @GetMapping("/followed")
    public String friendsPage(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        List<FollowedUserDTO> followedUsers = userService.getFollowedUsersWithPosts(currentUser.getId());
        model.addAttribute("followedUsers", followedUsers);
        model.addAttribute("isFriends", true);
        return "followed";
    }
        
    @GetMapping("/people")
    public String peoplePage(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            return "redirect:/login";
        }

        List<UserDTO> userList = userService.getAllUsers().stream()
            .filter(user -> !user.getId().equals(currentUser.getId()))
            .filter(user -> user.getId() != 1)
            .map(user -> new UserDTO(user.getId(), user.getUsername(), userService.isFollowing(currentUser, user)))
            .collect(Collectors.toList());

        model.addAttribute("isGuest", currentUser.getId() == 1);
        model.addAttribute("isPeople", true);
        model.addAttribute("users", userList);
        return "people";
    }


    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/home";
        }

        List<Post> userPosts = postService.findByUserNameOrderByCreationDateDesc(user);

        model.addAttribute("isGuest", currentUser.getId() == 1);
        model.addAttribute("user", user);
        model.addAttribute("posts", userPosts);
        
        return "profile";
    }


    @GetMapping("/user_main_page")
    public String login(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);
        model.addAttribute("posts", postService.findByUserNameOrderByCreationDateDesc(user));
        model.addAttribute("comments", commentService.findByUserName(user));
        return "user_main_page";
    }

    @GetMapping("/who_are_we")
    public String who(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("isGuest", user.getId() == 1);
        return "who_are_we";
    }

    @GetMapping("/registration_page")
    public String register() {
        return "registration_page";
    }
    @GetMapping("/edit_user_page")
    public String editUser(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "edit_user_page";
    }

}
