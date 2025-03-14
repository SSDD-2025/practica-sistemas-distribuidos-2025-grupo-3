package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private String postContent;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // Indica que es una fecha con hora
    private LocalDateTime creationDate;

    private String image;
    @Lob
    @Column(length = 1048576)
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userName;

    @ManyToOne
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    protected Post() {
        // Used by JPA
    }

    public Post(String title, String postContent, String image, byte[] imageData, User user, Community community) {
        this.title = title;
        this.postContent = postContent;
        this.image = image;
        this.imageData = imageData;
        this.userName = user;
        this.community = community;
        this.creationDate = LocalDateTime.now();
    }

    public String getPostContent() {
        return this.postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return userName;
    }

    public void setUser(User user) {
        this.userName = user;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return String.format("Post[id=%d, title='%s', postContent='%s']", id, title, postContent);
    }
}
