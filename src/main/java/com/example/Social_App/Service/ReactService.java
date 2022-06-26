package com.example.Social_App.Service;

import com.example.Social_App.Model.Comment;
import com.example.Social_App.Model.Post;
import com.example.Social_App.Model.React;
import com.example.Social_App.Model.User;
import com.example.Social_App.Repositiry.CommentRepository;
import com.example.Social_App.Repositiry.PostRepository;
import com.example.Social_App.Repositiry.ReactRepository;
import com.example.Social_App.Repositiry.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactService {


    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ReactRepository reactRepository;


    public ReactService(final ReactRepository reactRepository, final CommentRepository commentRepository, final PostRepository postRepository, final UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.reactRepository = reactRepository;
    }

    public List<React> getAllReacts() {
        return reactRepository.findAll();
    }


    public React createReactToPost(final React react, final Long id_User, final Long id_Post) {
        final User user = userRepository.findById(id_User).orElse(null);
        final Post post = postRepository.findById(id_Post).orElse(null);
        react.setOwner(user);
        react.setPost(post);
        return reactRepository.save(react);
    }

    public React createReactToComment(final React react, final Long id_User, final Long id_Comment) {
        final User user = userRepository.findById(id_User).orElse(null);
        final Comment comment = commentRepository.findById(id_Comment).orElse(null);
        react.setOwner(user);
        react.setComment(comment);
        return reactRepository.save(react);
    }

    public React findById(final Long id_React) {
        return reactRepository.findById(id_React).orElseThrow(() -> new RuntimeException("No data found!"));
    }

    public React updateReact(final Long id_React, final React react) {
        final React oldReact = reactRepository.findById(id_React).orElseThrow(() -> new RuntimeException("No data found!"));


        oldReact.setReactType(react.getReactType());

        return reactRepository.save(oldReact);
    }

    public void deleteReact(final Long id_React) {
        final React oldReact = reactRepository.findById(id_React).orElseThrow(() -> new RuntimeException("No data found!"));
        reactRepository.delete(oldReact);
    }

    public List<React> allReactOfAPost(final Long id_Post) {

        final Post post = postRepository.findById(id_Post).orElse(null);
        return reactRepository.findAllReactPerPost(post);
    }

    public List<React> allReactOfAComment(final Long id_Comment) {

        final Comment comment = commentRepository.findById(id_Comment).orElse(null);
        return reactRepository.findAllReactPerComment(comment);
    }

    public List<Post> findAllReactOfUserPerPost(final Long id_User) {

        final User user = userRepository.findById(id_User).orElse(null);
        return reactRepository.findAllReactOfUserPerPost(user);
    }

    public int numberOfReactPerPost(final Long id_Post) {
        final Post post = postRepository.findById(id_Post).orElse(null);
        return reactRepository.countPostReacts(post);


    }


}
