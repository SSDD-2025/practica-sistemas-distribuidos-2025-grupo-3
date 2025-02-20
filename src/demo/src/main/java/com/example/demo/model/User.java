package com.example.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

@Entity
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    private String username;
    private String password;
    private String email;

    @OneToMany(cascade=CascadeType.ALL)  private List<Post> posts;

    

    protected User() {
        // Used by JPA
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.posts = new ArrayList<>();
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
	public String toString() {
		return String.format("User[id=%d, username='%s', pasword='%s', email='%s']",
				id, username, password, email);
	}
}