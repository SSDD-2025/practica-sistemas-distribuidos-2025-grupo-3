package com.example.demo.DTO.Post;

import java.util.List;

import com.example.demo.model.Comment;
import com.example.demo.model.Community;
import com.example.demo.model.User;

public record PostDTO(Long id, String title, String postContent, User userName, Community community, List<Comment> comments) {

}
