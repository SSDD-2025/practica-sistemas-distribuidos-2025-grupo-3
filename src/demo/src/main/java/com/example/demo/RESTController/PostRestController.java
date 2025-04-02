package com.example.demo.RESTController;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Post.PostDTO;
import com.example.demo.DTO.Post.PostMapper;
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
    private PostMapper mapper;

    @GetMapping("/")
    public Collection<PostDTO> getAllPosts(){
        return mapper.toDTOs(postService.findAll());
    }

    @GetMapping("/{id}")
    public PostDTO getPost(@PathVariable Long id) {
        return mapper.toDTO(postService.findPostById(id));
    }

    @PostMapping("/")
    public ResponseEntity<PostDTO> createPostDTO(@RequestBody PostDTO postDTO) {
        postDTO = postService.createPost(postDTO.title(), postDTO.postContent(), null, null,null);
        
		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(postDTO.id()).toUri();

        return ResponseEntity.created(location).body(postDTO);
    }
    

    @PutMapping("/{id}")
    public PostDTO replacePost(@PathVariable Long id, @RequestBody PostDTO post) {
        return postService.replacePost(id,post);
    }

    @DeleteMapping("/{id}")
    public PostDTO deletePost(@PathVariable Long id){
        PostDTO postDTO = mapper.toDTO(postService.findPostById(id));
    
        postService.deletePost(id);

        return postDTO;
    }
    
}
