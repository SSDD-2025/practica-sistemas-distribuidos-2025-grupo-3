package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CommunityRepository;
import com.example.demo.model.Community;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    public Community findById(Long id) {
            return communityRepository.findById(id).orElse(null);
    }

    public List<Community> findAll() {
            return communityRepository.findAll();
    }

    public void createCommunity(String name){
        if((name != null && name.length() < 50) &&(!communityRepository.existsByName(name))){
            Community community = new Community(name);
            communityRepository.save(community);
        }
    }
    public void deleteById(Long id) {
        communityRepository.deleteById(id);
    }
}
