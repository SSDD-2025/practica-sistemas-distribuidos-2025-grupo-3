package com.example.demo.Controller;

import org.aspectj.internal.lang.annotation.ajcITD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.Community.CommunityDTO;
import com.example.demo.DTO.Community.CommunityMapper;
import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;
import com.example.demo.model.Community;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private CommunityMapper mapper;

    @PostMapping("/savePost")
    public String newPost(
            String title,
            String content,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            Long communityId,
            HttpServletRequest request) {

        String name = request.getUserPrincipal().getName();
        User user = userService.getUserByUsername(name);

        Community community = mapper.toDomain(communityService.findById(communityId));
        postService.createPost(title, content, imageFile, user, community);

        return "redirect:/communities/" + communityId;
    }

    @GetMapping("/post/image/{postId}")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long postId) {
        return postService.getPostImage(postId);
    }

    @PostMapping("/post/delete/{postId}")
    public String deletePost(@PathVariable Long postId, Long communityId) {
        postService.deletePost(postId);
        return "redirect:/communities/" + communityId;
    }

}