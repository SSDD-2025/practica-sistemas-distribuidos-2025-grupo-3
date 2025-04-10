package com.example.demo.RESTController;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Community.CommunityDTO;
import com.example.demo.DTO.Post.PostDTO;
import com.example.demo.DTO.Post.PostDTOBasic;
import com.example.demo.DTO.Post.PostDTORest;
import com.example.demo.DTO.Post.PostMapper;
import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    @Autowired
    private PostService postService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private PostMapper mapper;

    @GetMapping("/")
    public List<PostDTOBasic> getAllPosts() {
        return mapper.toDTOBasic(postService.findAll());
    }

    @GetMapping("/{id}")
    public PostDTOBasic getPost(@PathVariable Long id) {
        return mapper.toDTOBasic(postService.findPostById(id));
    }

    @PostMapping("/")
    public ResponseEntity<PostDTORest> postCommumnnityBasic(@RequestBody PostDTORest PostDTORest) {
        PostDTORest = postService.createPostDTORest(PostDTORest.title(), PostDTORest.postContent(),
                PostDTORest.community());
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(PostDTORest.id()).toUri();

        return ResponseEntity.created(location).body(PostDTORest);
    }

    @PutMapping("/{id}")
    public PostDTO replacePost(@PathVariable Long id, @RequestBody PostDTO post) {
        return postService.replacePost(id, post);
    }

    @DeleteMapping("/{id}")
    public PostDTO deletePost(@PathVariable Long id) {
        PostDTO postDTO = mapper.toDTO(postService.findPostById(id));

        postService.deletePost(id);

        return postDTO;
    }

}
