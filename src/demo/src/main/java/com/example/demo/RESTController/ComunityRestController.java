package com.example.demo.RESTController;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.DTO.Community.CommunityDTO;
import com.example.demo.DTO.Community.CommunityDTOBasic;
import com.example.demo.Service.CommunityService;
import com.example.demo.Service.UserService;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpServletRequest;

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

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<CommunityDTOBasic> getComunities() {
        return communityService.findAll();
    }

    @GetMapping("/{id}")
    public CommunityDTO getCommunity(@PathVariable Long id) {
        return communityService.findDTOById(id);
    }

    @PostMapping("/")
    public ResponseEntity<CommunityDTO> postCommumnnity(@RequestBody CommunityDTO communityDTO) {
        communityDTO = communityService.createCommunity(communityDTO.name());

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(communityDTO.id()).toUri();

        return ResponseEntity.created(location).body(communityDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommunityDTOBasic> replaceCommunity(@PathVariable Long id,
            @RequestBody CommunityDTOBasic communityDTO, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User currentUser = userService.getUserByUsername(principal.getName());

        if (!currentUser.getRoles().stream().anyMatch(role -> role.name().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this community.");
        }

        CommunityDTOBasic updatedCommunity = communityService.replaceCommunity(id, communityDTO);

        return ResponseEntity.ok(updatedCommunity);
    }

    @DeleteMapping("/{id}")
    public CommunityDTO deleteCommunity(@PathVariable Long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User currentUser = userService.getUserByUsername(principal.getName());

        if (!currentUser.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only admins can delete communities.");
        }

        return communityService.deleteById(id);
    }

}