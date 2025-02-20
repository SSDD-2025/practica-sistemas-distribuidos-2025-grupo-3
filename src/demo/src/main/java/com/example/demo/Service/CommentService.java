package com.example.demo.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;

@Service
public class CommentService {

    private Map<Long, Comment> comments = new HashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public CommentService(){}

    public Collection<Comment> findAll(){
        return comments.values();
    }

    public Comment findById(Long id){
        return comments.get(id);
    }

    public void deleteById(Long id){
        comments.remove(id);
    }

    public void save(Comment c){
        Long id = nextId.incrementAndGet();
        c.setId(id);
        comments.put(id, c);
    }
    
}

