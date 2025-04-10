package com.example.demo.RESTController;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Post.PostDTO;
import com.example.demo.DTO.Post.PostDTOBasic;
import com.example.demo.DTO.Post.PostDTORest;
import com.example.demo.DTO.Post.PostMapper;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostMapper mapper;

    @GetMapping("/")
    public Collection<PostDTO> getAllPosts() {
        return mapper.toDTOs(postService.findAll());
    }

    @GetMapping("/{id}")
    public PostDTOBasic getPost(@PathVariable Long id) {
        return mapper.toDTOBasic(postService.findPostById(id));
    }

    @PostMapping("/")
    public ResponseEntity<PostDTORest> postCommumnnityBasic(@RequestBody PostDTORest PostDTORest,
            HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User currentUser = userService.getUserByUsername(principal.getName());
        PostDTORest = postService.createPostDTORest(PostDTORest.title(), PostDTORest.postContent(), currentUser,
                PostDTORest.community());
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(PostDTORest.id()).toUri();

        return ResponseEntity.created(location).body(PostDTORest);
    }

    @DeleteMapping("/{id}")
    public PostDTORest deletePost(@PathVariable Long id) {
        PostDTORest postDTORest = mapper.toDTORest(postService.findPostById(id));

        postService.deletePost(id);

        return postDTORest;
    }

}
