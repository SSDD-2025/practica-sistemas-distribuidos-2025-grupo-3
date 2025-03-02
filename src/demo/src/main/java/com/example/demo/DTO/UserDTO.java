package com.example.demo.DTO;

public class UserDTO {
    private Long id;
    private String username;
    private boolean isFollowing;

    public UserDTO(Long id, String username, boolean isFollowing) {
        this.id = id;
        this.username = username;
        this.isFollowing = isFollowing;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public boolean isFollowing() { return isFollowing; }
}
