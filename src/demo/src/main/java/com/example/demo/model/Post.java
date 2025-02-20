package com.example.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Entity
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String postContent;

    protected Post(){
        // Used by JPA
    }

    public Post(String t,String c){
        this.title = t;
        this.postContent = c;
    }

    public String getContenido(){
        return this.postContent;
    }
    
    public void setContenido(String c){
        this.postContent = c;
    }

    public String getTitulo(){
        return this.title;
    }
    
    public void setTitulo(String t){
        this.title = t;
    }

    public Long getId(){
        return this.id;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    public String toString(){
        return String.format("Post[id=%d, title='%s', postContent='%s']",id,title,postContent);
    }
}
