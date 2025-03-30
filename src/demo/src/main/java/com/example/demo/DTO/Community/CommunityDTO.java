package com.example.demo.DTO.Community;

import java.util.List;

import com.example.demo.model.Post;
import com.example.demo.model.User;

public class CommunityDTO {
    private Long id;
    private String name;
    private List<Post> posts;
    private List<User> users;
    

    public CommunityDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Long getId(){
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    
}
