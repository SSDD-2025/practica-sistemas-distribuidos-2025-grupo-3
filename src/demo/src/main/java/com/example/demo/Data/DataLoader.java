package com.example.demo.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import com.example.demo.model.Community;
import com.example.demo.model.Post;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
    
    @Autowired
    PostService ps;

    @Autowired
    CommunityService cs;

    @PostConstruct
    public void run() throws Exception{
      Post post1 = new Post("Baloncesto", null, null, null, null, null);
      Post post2 = new Post("Futbol", null,null,null, null, null);

        Community c1 = new Community("Java");
        Community c2 = new Community("Futbol");
        Community c3 = new Community("Baloncesto");

        ps.save(post1);
        ps.save(post2);

        cs.save(c1);
        cs.save(c2);
        cs.save(c3);
    }
}
