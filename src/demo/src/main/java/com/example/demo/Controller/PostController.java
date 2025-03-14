package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import com.example.demo.model.Community;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommunityService communityService;

    @PostMapping("/savePost")
    public String newPost(
            String title,
            String content,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            Long communityId,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        Community community = communityService.findById(communityId);
        postService.createPost(title, content, imageFile, user, community);

        return "redirect:/communities/" + communityId;
    }

    @GetMapping("/post/image/{postId}")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long postId) {
        return postService.getPostImage(postId);
    }

    @PostMapping("/post/delete/{postId}")
    public String deletePost(
            @PathVariable Long postId,
            HttpSession session,
            Long communityId) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        postService.deletePost(postId);
        return "redirect:/communities/" + communityId;
    }

}