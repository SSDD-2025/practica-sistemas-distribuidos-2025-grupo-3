package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repository.CommunityRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.PostService;
import com.example.demo.model.Community;
import com.example.demo.model.User;

@Controller
public class PostController {
    
    @Autowired
    private PostService postService;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/savePost")
    public String newPost(
        @RequestParam("title") String title,
        @RequestParam("content") String content,
        @RequestParam(value = "image", required = false) MultipartFile imageFile,
        @RequestParam("communityId") Long communityId
    ) {
        // Obtener usuario (cambiar cuando haya autenticación)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener la comunidad
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Comunidad no encontrada"));

        postService.createPost(title, content, imageFile, user, community);

        return "redirect:/communities/" + communityId;
    }

    @GetMapping("/post/image/{postId}")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long postId) {
        return postService.getPostImage(postId);
    }
}