package com.example.demo.DTO.Community;

import java.util.List;

import com.example.demo.DTO.Post.PostDTORest;

public record CommunityDTO(
        Long id,
        String name,
        List<PostDTORest> posts) {

}
