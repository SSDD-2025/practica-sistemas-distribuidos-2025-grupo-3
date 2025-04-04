package com.example.demo.DTO.Post;

import java.util.List;

import com.example.demo.model.Comment;
import com.example.demo.model.Community;
import com.example.demo.model.User;

public record PostDTO(
                User owner,
                Community community,
                Long id,
                String creationDate,
                String title,
                String postContent,
                String image,
                Byte[] imageData,
                List<Comment> comments) {
        }
