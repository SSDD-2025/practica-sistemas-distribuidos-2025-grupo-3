package com.example.demo.model;

public class Post {

    private String title;
    private String postContent;

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
}
