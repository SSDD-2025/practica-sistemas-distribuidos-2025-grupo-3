package com.example.demo.model;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Entity
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    private String username;
    private String password;
    private String email;
    

    protected User() {
        // Used by JPA
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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


    @Override
	public String toString() {
		return String.format("Customer[id=%d, username='%s', pasword='%s', email='%s']",
				id, username, password, email);
	}
}