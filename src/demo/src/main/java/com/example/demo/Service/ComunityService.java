package com.example.demo.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.demo.model.Comunity;

@Service
public class ComunityService {

    private Map<Long, Comunity> comunities = new HashMap<>();
    private AtomicLong nextId = new AtomicLong();

    public ComunityService(){}

    public Collection<Comunity> findAll(){
        return comunities.values();
    }

    public Comunity findById(Long id){
        return comunities.get(id);
    }

    public void deleteById(Long id){
        comunities.remove(id);
    }

    public void save(Comunity c){
        Long id = nextId.incrementAndGet();
        c.setId(id);
        comunities.put(id, c);
    }
    
}
