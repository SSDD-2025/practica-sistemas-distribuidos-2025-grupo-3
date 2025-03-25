package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String deleteComment(@PathVariable Long commentId, Long communityId) {

        commentService.deleteComment(commentId);

        return "redirect:/communities/" + communityId;
    }

}