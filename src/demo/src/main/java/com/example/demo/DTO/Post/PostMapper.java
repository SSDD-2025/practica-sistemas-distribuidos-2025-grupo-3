package com.example.demo.DTO.Post;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.model.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO toDTO(Post post);

    Post toDomain(PostDTO postDTO);

    List<PostDTO> toDTOs(Collection<Post> posts);
}
