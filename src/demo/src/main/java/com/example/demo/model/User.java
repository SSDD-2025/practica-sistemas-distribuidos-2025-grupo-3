package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Date dateJoined;

    private String image;
    @Lob
    @Column(length = 1048576)
    private byte[] imageData;

    @OneToMany(mappedBy = "userName", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "userName", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_community",
        joinColumns = @JoinColumn(name = "username"),
        inverseJoinColumns = @JoinColumn(name = "community_id")
    )
    private List<Community> communities = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_friends",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "friend_id"})
    )
    private Set<User> friends = new HashSet<>();
    


    protected User() {
        // Used by JPA
    }

    public User(String username, String password, String email, Date dateJoined, String image, byte[] imageData) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateJoined = dateJoined;
        this.image = image;
        this.imageData = imageData;
    }

    public User(String username, String password, String email, Date dateJoined) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateJoined = dateJoined;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setImage(String image) {
        this.image = image;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
    
    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dateJoined=" + dateJoined +
                '}';
    }

}
