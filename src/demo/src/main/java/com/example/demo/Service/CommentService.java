package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.Comment.CommentDTO;
import com.example.demo.DTO.Comment.CommentMapper;
import com.example.demo.DTO.user.UserDTOBasic;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;

@Service
public class CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostService postService;


    public void createComment(String content, User user, Post post) {
        Comment comment = new Comment(content, user, post);
        commentRepository.save(comment);
    }

    public CommentDTO deleteComment(Long commentId) {
        CommentDTO commentDTO = commentMapper.toDTO(commentRepository.findById(commentId).orElseThrow());
        commentRepository.deleteById(commentId);
        return commentDTO;
    }


    public List<Comment> findByUserName(UserDTOBasic user) {
        return commentRepository.findByOwner(userRepository.findByUsername(user.username())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
    }

    public List<CommentDTO> findByUserId(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Comment> comments = commentRepository.findByOwner(user);
        return commentMapper.toDTOs(comments);
    }
    

    public List<CommentDTO> findAll() {
        return commentMapper.toDTOs(commentRepository.findAll());
    }

    public CommentDTO createCommentDTO(String commentContent, User currentUser, Long postId) {
        Post post = postService.findPostById(postId);
        Comment comment = new Comment(commentContent, currentUser, post);
        commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

}