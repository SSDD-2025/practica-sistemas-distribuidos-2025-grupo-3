package com.example.demo.model;


public class Community {
    private Long id;
    private String name;
    //coleccion con usuarios en la comunidad

    public Community(String n){
        this.name = n;
    }

    private String getName(){
        return this.name;
    }
    private void setName(String n){
        this.name = n;
    }

    public Long getId(){
        return this.id;
    }
    
    public void setId(Long id){
        this.id = id;
    }

}
