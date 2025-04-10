package com.example.demo.DTO.Post;

import com.example.demo.DTO.Community.CommunityDTOBasic;
import com.example.demo.model.User;

public record PostDTORest(
        Long id,
        String title,
        String postContent,
        User owner,
        CommunityDTOBasic community) {

}
