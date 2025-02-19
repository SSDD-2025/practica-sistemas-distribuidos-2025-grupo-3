package com.example.demo.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.demo.model.Post;

@Service
public class PostService {
    
    private Map<Long, Post> posts = new HashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public PostService(){}

    public Collection<Post> findAll(){
        return posts.values();
    }

    public Post findById(Long id){
        return posts.get(id);
    }

    public void deleteById(Long id){
        posts.remove(id);
    }

    public void save(Post p){
        Long id = nextId.incrementAndGet();
        p.setId(id);
        posts.put(id, p);
    }

}
