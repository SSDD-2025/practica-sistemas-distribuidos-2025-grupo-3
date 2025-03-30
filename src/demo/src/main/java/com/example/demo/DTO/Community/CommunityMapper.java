package com.example.demo.DTO.Community;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.model.Community;

@Mapper(componentModel = "spring")
public interface CommunityMapper {
    
    CommunityDTO toDTO (Community community);

    Community toDomain(CommunityDTO communityDTO);

    List<CommunityDTO> toDTOs(Collection<Community> communities);
    
} 
