package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.Comment.CommentDTO;
import com.example.demo.DTO.Comment.CommentMapper;
import com.example.demo.DTO.Post.PostMapper;
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

    @Autowired
    private PostMapper postMapper;

    public CommentDTO createComment(String commentContent, UserDTOBasic userDTOBasic, Long postId) {
        Post post = postMapper.toDomain(postService.findPostById(postId));
        User currentUser = userRepository.findByUsername(userDTOBasic.username()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado"));
        Comment comment = new Comment(commentContent, currentUser, post);
        commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    public CommentDTO deleteComment(Long commentId, UserDTOBasic userDTOBasic) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        boolean isOwner = comment.getOwner()
                .equals(userRepository.findByUsername(comment.getOwner().getUsername()).orElse(null));
        boolean isAdmin = userRepository.findByUsername(comment.getOwner().getUsername()).orElse(null).isAdmin();

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("No tienes permiso para eliminar este comentario");
        }

        commentRepository.delete(comment);
        return commentMapper.toDTO(comment);
    }

    public List<Comment> findByUserName(UserDTOBasic userDTOBasic) {
        return commentRepository.findByOwner(userRepository.findByUsername(userDTOBasic.username())
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

    public CommentDTO getCommentById(Long id) {
        return commentMapper.toDTO(commentRepository.findById(id).orElse(null));
    }

    public Page<CommentDTO> findByUserName(UserDTOBasic user, Pageable pageable) {
        User userEntity = userRepository.findByUsername(user.username())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Page<Comment> commentsPage = commentRepository.findByOwner(userEntity, pageable);
        return commentsPage.map(commentMapper::toDTO);
    }

}