package com.example.demo.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repository.PostRepository;
import com.example.demo.model.Community;
import com.example.demo.model.Post;
import com.example.demo.model.User;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public void createPost(String title, String content, MultipartFile imageFile, User user, Community community) {
        byte[] imageData = null;
        String imageName = null;

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                imageName = imageFile.getOriginalFilename();
                imageData = imageFile.getBytes();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar la imagen", e);
        }

        // Crear el post
        Post post = new Post(title, content, imageName, imageData, user, community);
        postRepository.save(post);
    }

    public ResponseEntity<byte[]> getPostImage(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        byte[] imageData = post.getImageData();
        if (imageData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg");
        return new ResponseEntity<byte[]>(imageData, headers, HttpStatus.OK);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<Post> findByUserOrderByCreationDateDesc(User user) {
        return postRepository.findByUserNameOrderByCreationDateDesc(user);
    }
}

