package com.example.demo.DTO.Post;

import com.example.demo.DTO.Community.CommunityDTOBasic;
import com.example.demo.DTO.user.UserDTOBasic;

public record PostDTOBasic(Long id,
                String title,
                String postContent,
                String image,
                String creationDate,
                CommunityDTOBasic community,
                UserDTOBasic owner) {

}
