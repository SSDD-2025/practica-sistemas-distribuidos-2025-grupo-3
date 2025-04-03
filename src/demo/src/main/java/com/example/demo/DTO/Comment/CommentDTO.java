package com.example.demo.DTO.Comment;

public record CommentDTO(Long id,
        String commentContent,
        Long postId) {

}