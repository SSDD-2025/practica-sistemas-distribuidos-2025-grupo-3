package com.example.demo.RESTController;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.DTO.Comment.CommentDTO;
import com.example.demo.Service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public Collection<CommentDTO> getAllComments() {
        return commentService.findAll();
    }
}