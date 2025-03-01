package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Repository.PostRepository;
import com.example.demo.Service.CommentService;
import com.example.demo.model.Post;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired 
    private PostRepository postRepository;
    
    @PostMapping("/saveComment")
    public String createComment(String content, Long postId, HttpSession session) {
        
        User user = (User) session.getAttribute("user");
        
        Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        commentService.createComment(content, user, post);
        
        return "redirect:/communities/" + post.getCommunity().getId();
    }

}