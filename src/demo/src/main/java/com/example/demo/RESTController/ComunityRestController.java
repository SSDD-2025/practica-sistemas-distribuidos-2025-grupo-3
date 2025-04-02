package com.example.demo.RESTController;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Community.CommunityDTO;
import com.example.demo.Service.CommunityService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/communities")
public class ComunityRestController {
    @Autowired
    private CommunityService communityService;

    @GetMapping("/")
    public Collection<CommunityDTO> getComunities() {
        return communityService.findAll();
    }

    @GetMapping("/{id}")
    public CommunityDTO getCommunity(@PathVariable Long id) {
        return communityService.findById(id);
    }
    
    @PostMapping("/")
    public ResponseEntity<CommunityDTO> postCommumnnity(@RequestBody CommunityDTO communityDTO) {
        communityDTO = communityService.createCommunity(communityDTO.name());

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(communityDTO.id()).toUri();

        return ResponseEntity.created(location).body(communityDTO);
    }
    
    @PutMapping("/{id}")
    public CommunityDTO replaceCommunity(@PathVariable Long id, @RequestBody CommunityDTO communityDTO) {
        return communityService.replaceCommunity(id, communityDTO);
    }

    @DeleteMapping("/{id}")
    public CommunityDTO deleteCommunity(@PathVariable Long id){
        return communityService.deleteById(id);
    }
    

    
}