package com.example.demo.DTO.Comment;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "comment", target = "commentContent") 
    @Mapping(source = "post.id", target = "postId") 
    @Mapping(source = "owner.username", target = "owner.username") 
    CommentDTO toDTO(Comment comment);

    @Mapping(source = "commentContent", target = "comment")
    @Mapping(source = "postId", target = "post.id") 
    Comment toDomain(CommentDTO commentDTO);

    List<CommentDTO> toDTOs(Collection<Comment> comments);
}
 
