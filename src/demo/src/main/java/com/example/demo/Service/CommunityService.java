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
    private CommunityMapper mapper;

    public CommunityDTOBasic findDTOBasicById(Long id) {
        return mapper.toDTOBasic(communityRepository.findById(id).orElse(null));
    }

    public CommunityDTO findDTOById(Long id) {
        return mapper.toDTO(communityRepository.findById(id).orElse(null));
    }

    public List<CommunityDTOBasic> findAll() {
        return mapper.toDTOsBasic(communityRepository.findAll());
    }

    public CommunityDTO createCommunity(String name) {
        if ((name != null && name.length() < 50) && (!communityRepository.existsByName(name))) {
            Community community = new Community(name);
            communityRepository.save(community);
            return mapper.toDTO(community);
        }
        return null;
    }

    public CommunityDTOBasic replaceCommunity(Long id, CommunityDTOBasic updaCommunityDTO) {
        if (communityRepository.existsById(id)) {
            Community updatedCommunity = mapper.toDomain(updaCommunityDTO);
            updatedCommunity.setId(id);

            communityRepository.save(updatedCommunity);

            return mapper.toDTOBasic(updatedCommunity);
        } else {
            throw new NoSuchElementException();
        }
    }

    public CommunityDTO deleteById(Long id) {
        CommunityDTO communityDTO = mapper.toDTO(communityRepository.findById(id).orElseThrow());
        communityRepository.deleteById(id);
        return communityDTO;
    }
}
