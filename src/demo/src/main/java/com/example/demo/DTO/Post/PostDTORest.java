package com.example.demo.DTO.Post;

import com.example.demo.DTO.Community.CommunityDTOBasic;
import com.example.demo.DTO.user.UserDTORest;

public record PostDTORest(
                Long id,
                String title,
                String postContent,
                String creationDate,
                UserDTORest owner,
                CommunityDTOBasic community) {
}
