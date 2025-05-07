package com.example.demo.DTO.Post;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.model.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "creationDate", source = "creationDate", dateFormat = "dd/MM/yyyy HH:mm")
    PostDTO toDTO(Post post);

    Post toDomain(PostDTO postDTO);

    Post toDomain(PostDTORest postDTORest);

    PostDTORest toDTORest(PostDTO post);

    PostDTORest toDTORest(Post post);

    @Mapping(target = "creationDate", source = "creationDate", dateFormat = "dd/MM/yyyy HH:mm")
    List<PostDTO> toDTOs(Collection<Post> posts);

}
