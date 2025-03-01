package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CommentRepository;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    public void createComment(String content, User user, Post post) {
        Comment comment = new Comment(content, user, post);
        commentRepository.save(comment);
    
    }
}