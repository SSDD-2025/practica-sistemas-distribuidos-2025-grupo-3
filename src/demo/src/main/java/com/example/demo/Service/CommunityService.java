package com.example.demo.Service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.Community.CommunityDTO;
import com.example.demo.DTO.Community.CommunityDTOBasic;
import com.example.demo.DTO.Community.CommunityMapper;
import com.example.demo.Repository.CommunityRepository;
import com.example.demo.model.Community;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private CommunityMapper communityMapper;

    public CommunityDTOBasic findDTOBasicById(Long id) {
        return communityMapper.toDTOBasic(communityRepository.findById(id).orElse(null));
    }

    public CommunityDTO findDTOById(Long id) {
        return communityMapper.toDTO(communityRepository.findById(id).orElse(null));
    }

    public List<CommunityDTOBasic> findAll() {
        return communityMapper.toDTOsBasic(communityRepository.findAll());
    }

    public CommunityDTO createCommunity(String name) {
        if ((name != null && name.length() < 50) && (!communityRepository.existsByName(name))) {
            Community community = new Community(name);
            communityRepository.save(community);
            return communityMapper.toDTO(community);
        }
        return null;
    }

    public CommunityDTOBasic replaceCommunity(Long id, CommunityDTOBasic updaCommunityDTO) {
        if (communityRepository.existsById(id)) {
            Community updatedCommunity = communityMapper.toDomain(updaCommunityDTO);
            updatedCommunity.setId(id);

            communityRepository.save(updatedCommunity);

            return communityMapper.toDTOBasic(updatedCommunity);
        } else {
            throw new NoSuchElementException();
        }
    }

    public CommunityDTO deleteById(Long id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Community not found with id: " + id));
        communityRepository.delete(community);
        return communityMapper.toDTO(community);
    }

    public Community findById(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Community not found with id: " + id));
    }
}
