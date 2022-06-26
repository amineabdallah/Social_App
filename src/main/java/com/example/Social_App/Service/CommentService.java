package com.example.Social_App.Service;


import com.example.Social_App.Model.Comment;
import com.example.Social_App.Model.Post;
import com.example.Social_App.Model.User;
import com.example.Social_App.Repositiry.CommentRepository;
import com.example.Social_App.Repositiry.PostRepository;
import com.example.Social_App.Repositiry.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository ,PostRepository postRepository,UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<Comment> getAllCommets(){
        return commentRepository.findAll();
    }

    public Comment createComment(Comment comment,Long id_User,Long id_Post){
        LocalDateTime date = LocalDateTime.now();
        User user = userRepository.findById(id_User).orElse(null);
        Post post = postRepository.findById(id_Post).orElse(null);
        comment.setOwner(user);
        comment.setPost(post);
        comment.setCreatedAt(date);
        return commentRepository.save(comment);
    }

    public Comment findById(Long id_Comment){
        return commentRepository.findById(id_Comment).orElseThrow(() -> new RuntimeException("No data found!"));
    }

    public Comment updateComment(Long id_Comment, Comment comment){
        Comment oldComment = commentRepository.findById(id_Comment).orElseThrow(() -> new RuntimeException("No data found!"));


        LocalDateTime date = LocalDateTime.now();
        oldComment.setContent(comment.getContent());
        oldComment.setUpdatedAt(date);

        return commentRepository.save(oldComment);
    }

    public void deleteComment(Long id_Comment){
        Comment oldComment = commentRepository.findById(id_Comment).orElseThrow(() -> new RuntimeException("No data found!"));
        commentRepository.delete(oldComment);
    }

    public List<Comment> allCommentOfAPost(Long id_Post) {

        Post post = postRepository.findById(id_Post).orElse(null);
        return  commentRepository.findAllCommentPerPost(post);
    }


}
