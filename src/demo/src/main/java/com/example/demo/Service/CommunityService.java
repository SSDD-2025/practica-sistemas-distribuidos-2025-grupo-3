package com.example.demo.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.Community.CommunityDTO;
import com.example.demo.DTO.Community.CommunityMapper;
import com.example.demo.Repository.CommunityRepository;
import com.example.demo.model.Community;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;
    
    @Autowired
    private CommunityMapper mapper;

    public CommunityDTO findById(Long id) {
        return mapper.toDTO(communityRepository.findById(id).orElse(null));
    }

    public Collection<CommunityDTO> findAll() {
        return mapper.toDTOs(communityRepository.findAll());
    }

    public CommunityDTO createCommunity(String name) {
        if ((name != null && name.length() < 50) && (!communityRepository.existsByName(name))) {
            Community community = new Community(name);
            communityRepository.save(community);
            return mapper.toDTO(community);
        }
        return null;
    }

    public CommunityDTO replaceCommunity(Long id, CommunityDTO updaCommunityDTO){
        if(communityRepository.existsById(id)){
           Community updatedCommunity = mapper.toDomain(updaCommunityDTO);
           updatedCommunity.setId(id);

           communityRepository.save(updatedCommunity);

           return mapper.toDTO(updatedCommunity);
        }else{
            throw new NoSuchElementException();
        }
    }

    public CommunityDTO deleteById(Long id) {
        CommunityDTO communityDTO = mapper.toDTO(communityRepository.findById(id).orElseThrow());
        communityRepository.deleteById(id);
        return communityDTO;
    }
}
