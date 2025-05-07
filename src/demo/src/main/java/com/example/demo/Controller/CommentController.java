package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.user.UserDTOBasic;
import com.example.demo.Service.CommentService;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/saveComment")
    public String createComment(String content, Long postId, HttpServletRequest request) {

        String name = request.getUserPrincipal().getName();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(name);
        commentService.createComment(content, userDTOBasic, postId);

        String referer = request.getHeader("Referer");

        return "redirect:" + (referer != null ? referer : "/");
    }

    @PostMapping("/comment/deleteComment/{commentId}")
    public String deleteComment(@PathVariable Long commentId,
            @RequestParam(required = false) Long communityId,
            HttpServletRequest request) {

        String name = request.getUserPrincipal().getName();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(name);

        commentService.deleteComment(commentId, userDTOBasic);

        if (communityId != null) {
            return "redirect:/communities/" + communityId;
        } else {
            return "redirect:/myComments";
        }
    }

}