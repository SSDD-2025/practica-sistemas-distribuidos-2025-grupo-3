package com.example.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Entity
public class Comment {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    private String comment;
    private User user;

    protected Comment() {
        // Used by JPA
    }

    public Comment(String comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public String getComment() {
        return this.comment;
    }

    public User getUser() {
        return this.user;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    @Override
	public String toString() {
		return String.format("Comment[id=%d, comment='%s', user='%s']",
				id, comment, user);
	}
}