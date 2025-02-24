package com.example.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Entity;

@Entity
public class Comment  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User userName;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    protected Comment() {
        // Used by JPA
    }

    public Comment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return this.comment;
    }

    public User getUser() {
        return this.userName;
    }

    public Long getId() {
        return this.id;
    }
   

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void SetUser(User user) {
        this.userName = user;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    

    @Override
	public String toString() {
		return String.format("Comment[id=%d, comment='%s', user='%s']",
				id, comment);
	}
}