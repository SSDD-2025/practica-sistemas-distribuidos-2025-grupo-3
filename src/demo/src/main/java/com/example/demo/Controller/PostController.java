package com.example.demo.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repository.CommunityRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.model.Community;
import com.example.demo.model.Post;
import com.example.demo.model.User;

@Controller
public class PostController {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @PostMapping("/savePost")
    public String newPost(
        @RequestParam("content") String postContent,
        @RequestParam(value = "image", required = false) MultipartFile imageFile,
        @RequestParam("communityId") Long communityId,
        Model model
        //@AuthenticationPrincipal User user // Recibir usuario para cuando haya sistema de autenticacion
        ) {

            User user = new User("username", "password", "email", null);
            Community community = communityRepository.findById(communityId)
            .orElseThrow(() -> new RuntimeException("Comunidad no encontrada"));
            byte[] imageData = null;
            String imageName = null;

            try {
                if (imageFile != null && !imageFile.isEmpty()) {
                    imageName = imageFile.getOriginalFilename();
                    imageData = imageFile.getBytes();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Crea el post con los datos capturados
            Post post = new Post(
                    null, // El título no está en el formulario
                    postContent,
                    imageName,
                    imageData,
                    user,
                    community
            );
            //Guarda el post
                postRepository.save(post);
            //Devuelve la comniudad en la que estabas
                model.addAttribute("community", community);

            return "community";
        }
}
