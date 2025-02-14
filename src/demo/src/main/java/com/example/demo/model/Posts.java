package com.example.demo.model;

public class Posts {
    private String contenido;

    public Posts(String c){
        this.contenido = c;
    }

    public String getContenido(){
        return this.contenido;
    }
    
    public void setContenido(String c){
        this.contenido = c;
    }
}
