package com.example.demo.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Service.ComunityService;
import com.example.demo.Service.PostService;
import com.example.demo.model.Comunity;
import com.example.demo.model.Post;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
    
    @Autowired
    PostService ps;
    
    @Autowired
    ComunityService cs;

    @PostConstruct
    public void run() throws Exception{
        Post post1 = new Post("Titulo ejemplo", "contenido ejemplo");
        Post post2 = new Post("Titulo ejemplo2", "contenido ejemplo2");

        Comunity c1 = new Comunity("Java");
        Comunity c2 = new Comunity("Futbol");

        ps.save(post1);
        ps.save(post2);

        cs.save(c1);
        cs.save(c2);
    }
}
