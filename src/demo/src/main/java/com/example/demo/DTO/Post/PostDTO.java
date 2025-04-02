package com.example.demo.DTO.Post;

<<<<<<< HEAD
public record PostDTO(Long id,
        String title,
        String postContent) {
=======
import com.example.demo.model.Community;
import com.example.demo.model.User;

public record PostDTO(Long id, String title, String postContent, User userName, Community community) {
>>>>>>> 0b8e1c5c0c05a6a18cc76d47d9245ec717b66d3c

}
