package com.example.demo.DTO.Community;

import java.util.List;

import com.example.demo.DTO.Post.PostDTO;

public record CommunityDTO(
                Long id,
                String name,
                List<PostDTO> posts) {

}
