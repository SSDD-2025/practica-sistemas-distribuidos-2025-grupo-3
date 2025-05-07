package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.Community.CommunityDTOBasic;
import com.example.demo.DTO.user.UserDTOBasic;
import com.example.demo.Service.CommunityService;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PostController {

    @Autowired
    private UserService userService;

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
            HttpServletRequest request) {

        String name = request.getUserPrincipal().getName();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(name);

        CommunityDTOBasic communityDTOBasic = communityService.findDTOBasicById(communityId);
        postService.createPost(title, content, imageFile, userDTOBasic, communityDTOBasic);

        return "redirect:/communities/" + communityId;
    }

    @GetMapping("/post/image/{postId}")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long postId) {
        return postService.getPostImage(postId);
    }

    @PostMapping("/post/delete/{postId}")
    public String deletePost(
            @PathVariable Long postId,
            @RequestParam(required = false) Long communityId,
            HttpServletRequest request) {

        String name = request.getUserPrincipal().getName();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(name);

        postService.deletePost(postId, userDTOBasic);

        if (communityId != null) {
            return "redirect:/communities/" + communityId;
        } else {
            return "redirect:/myPosts";
        }

    }

}
