package com.example.demo.DTO.Post;

import com.example.demo.DTO.Community.CommunityDTOBasic;

public record PostDTORest(Long id,
String title,
String postContent,
CommunityDTOBasic community) {

}
