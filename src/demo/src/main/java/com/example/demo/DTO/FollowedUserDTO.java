package com.example.demo.DTO;

import java.util.List;

import com.example.demo.model.Post;

public class FollowedUserDTO {
    private Long id;
    private String username;
    private byte[] imageData;
    private List<Post> recentPosts;

    // Constructor, getters y setters
    public FollowedUserDTO(Long id, String username, byte[] imageData, List<Post> recentPosts) {
        this.id = id;
        this.username = username;
        this.imageData = imageData;
        this.recentPosts = recentPosts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getProfilePictureUrl() {
        return imageData;
    }

    public void setProfilePictureUrl(byte[] imageData) {
        this.imageData = imageData;
    }

    public List<Post> getRecentPosts() {
        return recentPosts;
    }

    public void setRecentPosts(List<Post> recentPosts) {
        this.recentPosts = recentPosts;
    }
}