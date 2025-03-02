package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CommentRepository;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    public void createComment(String content, User user, Post post) {
        Comment comment = new Comment(content, user, post);
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        /*Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        
         if (comment.getUser().getId() != user.getId()) {
            throw new RuntimeException("No puedes borrar un comentario que no es tuyo");        para que no se pueda borrar un comentario que no sea del usuario
        } */
        commentRepository.deleteById(commentId);
        }
}