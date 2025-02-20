package com.example.demo.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Entity
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    

    public Community(String n){
        this.name = n;
    }

    protected Community(){
        // Used by JPA
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

    public String toString(){
        return String.format("Community[id=%d, name='%s']", id, name);
    }

}
