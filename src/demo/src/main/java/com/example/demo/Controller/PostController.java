package com.example.demo.Controller;

import java.io.IOException;
import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repository.CommunityRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Community;
import com.example.demo.model.Post;
import com.example.demo.model.User;

@Controller
public class PostController {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/savePost")
    public String newPost(
        @RequestParam("title") String postTitle,
        @RequestParam("content") String postContent,
        @RequestParam(value = "image", required = false) MultipartFile imageFile,
        @RequestParam("communityId") Long communityId,
        Model model
        //@AuthenticationPrincipal User user // Recibir usuario para cuando haya sistema de autenticacion
        ) {

            User user = userRepository.findById(1L).get(); // De momento por defecto, mas adelante cambiar por el usuario autenticado
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
                    postTitle,
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
                model.addAttribute("posts", community.getPosts());
                return "redirect:/communities/" + communityId;
        }

    @GetMapping("/post/image/{postId}")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        byte[] imageData = post.getImageData();
        if (imageData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/jpeg"); // Adjust MIME type according to the stored image type
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}
