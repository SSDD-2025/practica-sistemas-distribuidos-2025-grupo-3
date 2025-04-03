package com.example.demo.DTO.Comment;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    
    CommentDTO toDTO (Comment Comment);

    Comment toDomain(CommentDTO commentDTO);

    List<CommentDTO> toDTOs(Collection<Comment> comments);
    
} 
