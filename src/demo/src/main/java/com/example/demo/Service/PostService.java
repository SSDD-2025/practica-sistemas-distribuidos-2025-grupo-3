package com.example.demo.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.Post.PostDTO;
import com.example.demo.DTO.Post.PostMapper;
import com.example.demo.DTO.user.UserDTOBasic;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Community;
import com.example.demo.model.Post;
import com.example.demo.model.User;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostMapper mapper;

    public PostDTO createPost(String title, String content, MultipartFile imageFile, User user, Community community) {
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

        Post post = new Post(title, content, imageName, imageData, user, community);
        postRepository.save(post);
        return mapper.toDTO(post);
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

    public void deletePost(Long postId, String username) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post no encontrado"));

        boolean isOwner = post.getOwner().getUsername().equals(username);
        boolean isAdmin = userService.isAdmin(username);

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("No tienes permiso para eliminar este post");
        }

        postRepository.delete(post);
    }


    public List<PostDTO> findByUserOrderByCreationDateDesc(User user) {
        List<Post> posts = postRepository.findByOwnerOrderByCreationDateDesc(user);
        return mapper.toDTOs(posts);
    }

    public List<PostDTO> findTop5ByOrderByCreationDateDesc() {
        List<Post> posts = postRepository.findTop5ByOrderByCreationDateDesc();
        return mapper.toDTOs(posts);
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));
    }

    public List<PostDTO> findByCommunityIdOrderByCreationDateDesc(Long id) {
        List<Post> posts = postRepository.findByCommunityIdOrderByCreationDateDesc(id);
        return mapper.toDTOs(posts);
    }

    public List<PostDTO> findByUserNameOrderByCreationDateDesc(UserDTOBasic user) {
        List<Post> posts = postRepository.findByOwnerOrderByCreationDateDesc(userRepository.findByUsername(user.username())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        return mapper.toDTOs(posts);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public PostDTO replacePost(Long id, PostDTO updatedPostDTO) {
                if(postRepository.existsById(id)){
           Post updatedPost = mapper.toDomain(updatedPostDTO);
           updatedPost.setId(id);

           postRepository.save(updatedPost);

           return mapper.toDTO(updatedPost);
        }else{
            throw new NoSuchElementException();
        }
    }
}
