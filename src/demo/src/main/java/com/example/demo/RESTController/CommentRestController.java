package com.example.demo.RESTController;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.DTO.Comment.CommentDTO;
import com.example.demo.DTO.user.UserDTOBasic;
import com.example.demo.Service.CommentService;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public Collection<CommentDTO> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public Collection<CommentDTO> getCommentsByUsername(@PathVariable Long id) {
        return commentService.findByUserId(id);
    }

    @PostMapping("/")
    public ResponseEntity<CommentDTO> postComment(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        UserDTOBasic currentUser = userService.getUserByUsername(principal.getName());
        commentDTO = commentService.createComment(commentDTO.commentContent(), currentUser, commentDTO.postId());

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(commentDTO.id()).toUri();

        return ResponseEntity.created(location).body(commentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        UserDTOBasic userDTOBasic = userService.getUserByUsername(principal.getName());

        CommentDTO commentDTO = commentService.deleteComment(id, userDTOBasic);
        if (commentDTO == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't delete this comment");
        }
    }

}