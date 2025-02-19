package com.example.demo.model;

public class Comment {

    private String comment;
    private User user;

    public Comment(String comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}