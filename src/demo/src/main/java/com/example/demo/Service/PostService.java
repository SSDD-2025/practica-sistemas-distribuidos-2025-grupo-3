package com.example.demo.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.Community.CommunityDTOBasic;
import com.example.demo.DTO.Community.CommunityMapper;
import com.example.demo.DTO.Post.PostDTO;
import com.example.demo.DTO.Post.PostDTORest;
import com.example.demo.DTO.Post.PostMapper;
import com.example.demo.DTO.user.UserMapper;
import com.example.demo.DTO.user.UserDTOBasic;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Community;
import com.example.demo.model.Post;
import com.example.demo.model.User;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private UserMapper userMapper;

    public PostDTO createPost(String title, String content, MultipartFile imageFile, UserDTOBasic userDTOBasic,
            CommunityDTOBasic communityDTObasic) {
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

        User user = userMapper.toDomain(userDTOBasic);
        Community community = communityMapper.toDomain(communityDTObasic);
        Post post = new Post(title, content, imageName, imageData, user, community);
        postRepository.save(post);
        return postMapper.toDTO(post);
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

    public PostDTO deletePost(Long postId, UserDTOBasic userDTOBasic) {

        Post post = postRepository.findById(postId).orElse(null);
        boolean isOwner = post.getOwner().equals(userMapper.toDomain(userDTOBasic));
        boolean isAdmin = userService.isAdmin(userDTOBasic);

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("No tienes permiso para eliminar este post");
        }
        postRepository.delete(post);
        return postMapper.toDTO(post);
    }

    public List<PostDTO> findByUserOrderByCreationDateDesc(User user) {
        List<Post> posts = postRepository.findByOwnerOrderByCreationDateDesc(user);
        return postMapper.toDTOs(posts);
    }

    public List<PostDTO> findTop5ByOrderByCreationDateDesc() {
        List<Post> posts = postRepository.findTop5ByOrderByCreationDateDesc();
        return postMapper.toDTOs(posts);
    }

    public PostDTO findPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(null);
        return postMapper.toDTO(post);
    }

    public List<PostDTO> findByCommunityIdOrderByCreationDateDesc(Long id) {
        List<Post> posts = postRepository.findByCommunityIdOrderByCreationDateDesc(id);
        return postMapper.toDTOs(posts);
    }

    public Page<PostDTO> findByCommunityIdWithPagination(Long communityId, Pageable pageable) {
        Page<Post> postsPage = postRepository.findByCommunityIdOrderByCreationDateDesc(communityId, pageable);
        return postsPage.map(postMapper::toDTO);
    }

    public List<PostDTO> findByUserNameOrderByCreationDateDesc(UserDTOBasic user) {
        List<Post> posts = postRepository
                .findByOwnerOrderByCreationDateDesc(userRepository.findByUsername(user.username())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        return postMapper.toDTOs(posts);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public PostDTO replacePost(Long id, PostDTO updatedPostDTO) {
        if (postRepository.existsById(id)) {
            Post updatedPost = postMapper.toDomain(updatedPostDTO);
            updatedPost.setId(id);

            postRepository.save(updatedPost);

            return postMapper.toDTO(updatedPost);
        } else {
            throw new NoSuchElementException();
        }
    }

    public PostDTORest createPostDTORest(String title, String postContent,
            UserDTOBasic userDTOBasic,
            CommunityDTOBasic communityDTObasic) {

        User owner = userMapper.toDomain(userDTOBasic);
        Community community = communityMapper.toDomain(communityDTObasic);

        Post post = new Post(title, postContent, null, null, owner, community);

        postRepository.save(post);

        return postMapper.toDTORest(post);
    }

    public Page<PostDTO> findByUserNameOrderByCreationDateDesc(UserDTOBasic user, Pageable pageable) {
        Page<Post> postsPage = postRepository
                .findByOwnerOrderByCreationDateDesc(userRepository.findByUsername(user.username())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado")), pageable);
        return postsPage.map(postMapper::toDTO);
    }
}
