package com.example.demo.Controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.ui.Model;

import com.example.demo.DTO.Comment.CommentDTO;
import com.example.demo.DTO.Post.PostDTO;
import com.example.demo.DTO.user.UserDTOBasic;

import com.example.demo.Service.CommentService;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(String username, String email, String password, HttpServletRequest request, Model model)
            throws IOException {
        boolean[] registrationStatus = new boolean[] { false, false, false };

        userService.registerUser(username, email, password, registrationStatus);

        model.addAttribute("errorUser", registrationStatus[0]);
        model.addAttribute("errorMail", registrationStatus[1]);
        model.addAttribute("registrationSuccess", registrationStatus[2]);

        return "index";
    }

    @GetMapping("/editUserPage")
    public String editUser(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", userDTOBasic);
        return "editUserPage";
    }

    @PostMapping("/editUser")
    public String editUser(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            HttpServletRequest request,
            Model model) throws IOException {

        UserDTOBasic user = userService.getUserByUsername(request.getUserPrincipal().getName());

        userService.editUser(user, email, password, imageFile);

        return "redirect:/userMainPage";

    }

    @PostMapping("/user/delete")
    public String deleteUser(HttpServletRequest request) {
        UserDTOBasic user = userService.getUserByUsername(request.getUserPrincipal().getName());
        userService.deleteUser(user.id());

        return "redirect:/";
    }

    @PostMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);

        return "redirect:/admin?mensaje=Usuario eliminado correctamente";
    }

    @PostMapping("/follow/{id}")
    public String toggleFollow(@PathVariable Long id, HttpServletRequest request) {

        String name = request.getUserPrincipal().getName();
        UserDTOBasic user = userService.getUserByUsername(name);

        userService.toggleFollow(user.id(), id);
        return "redirect:/people";
    }

    @GetMapping("/user/image/{userId}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long userId) {
        return userService.getUserImage(userId);
    }

    @GetMapping("/myPosts")
    public String myPosts(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Principal principal = request.getUserPrincipal();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(principal.getName());
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> postsPage = postService.findByUserNameOrderByCreationDateDesc(userDTOBasic, pageable);

        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("postsBoolean", postsPage.hasContent());
        model.addAttribute("currentPage", postsPage.getNumber() + 1);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        model.addAttribute("previousPage", postsPage.hasPrevious() ? postsPage.getNumber() - 1 : 0);
        model.addAttribute("nextPage", postsPage.getTotalPages() > 0 && postsPage.hasNext() ? postsPage.getNumber() + 1
                : postsPage.getTotalPages() - 1);
        model.addAttribute("user", userDTOBasic);
        return "userPosts";
    }

    @GetMapping("/myComments")
    public String myComments(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Principal principal = request.getUserPrincipal();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(principal.getName());
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentDTO> commentPage = commentService.findByUserName(userDTOBasic, pageable);

        model.addAttribute("comments", commentPage.getContent());
        model.addAttribute("commentsBoolean", commentPage.hasContent());
        model.addAttribute("currentPage", commentPage.getNumber() + 1);
        model.addAttribute("totalPages", commentPage.getTotalPages());
        model.addAttribute("previousPage", commentPage.hasPrevious() ? commentPage.getNumber() - 1 : 0);
        model.addAttribute("nextPage",
                commentPage.getTotalPages() > 0 && commentPage.hasNext() ? commentPage.getNumber() + 1
                        : commentPage.getTotalPages() - 1);
        model.addAttribute("user", userDTOBasic);
        return "userComments";
    }
}
