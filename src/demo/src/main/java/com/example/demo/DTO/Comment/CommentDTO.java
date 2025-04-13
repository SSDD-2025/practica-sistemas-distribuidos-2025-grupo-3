package com.example.demo.DTO.Comment;

import com.example.demo.DTO.user.UserDTORest;

public record CommentDTO(
                Long id,
                String commentContent,
                Long postId,
                UserDTORest owner) {
}
