package com.example.demo.Controller;

import java.security.Principal;

import com.example.demo.model.User.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.CommentService;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;
import com.example.demo.model.Post;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @PostMapping("/saveComment")
    public String createComment(String content, Long postId, HttpServletRequest request) {

        String name = request.getUserPrincipal().getName();
        User user = userService.getUserByUsername(name);
        Post post = postService.findPostById(postId);

        commentService.createComment(content, user, post);

        return "redirect:/communities/" + post.getCommunity().getId();
    }

    @PostMapping("/comment/deleteComment/{commentId}")
    public String deleteComment(@PathVariable Long commentId,
            @RequestParam(required = false) Long communityId,
            Principal principal) {

        commentService.deleteComment(commentId);

        User currentUser = userService.getUserByUsername(principal.getName());

        if (currentUser.getRoles().contains(Role.ROLE_ADMIN) && communityId != null) {
            return "redirect:/communities/" + communityId;
        } else {
            return "redirect:/myComments";
        }
    }

}