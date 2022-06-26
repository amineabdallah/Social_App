package com.example.Social_App.Service;

import com.example.Social_App.Model.Post;
import com.example.Social_App.Model.User;
import com.example.Social_App.Repositiry.PostRepository;
import com.example.Social_App.Repositiry.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {


    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public PostService(final PostRepository postRepository, final UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(final Post post, final Long id_User) {
        final LocalDateTime date = LocalDateTime.now();
        final User user = userRepository.findById(id_User).orElseThrow(() -> new RuntimeException("No data found!"));
        post.setOwner(user);
        post.setCreatedAt(date);
        return postRepository.save(post);
    }

    public Post findById(final Long id_Post) {
        return postRepository.findById(id_Post).orElseThrow(() -> new RuntimeException("No data found!"));
    }

    public Post updatePost(final Long id_Post, final Post post) {
        final Post oldPost = postRepository.findById(id_Post).orElseThrow(() -> new RuntimeException("No data found!"));


        final LocalDateTime date = LocalDateTime.now();
        oldPost.setContent(post.getContent());
        oldPost.setUpdatedAt(date);


        return postRepository.save(oldPost);
    }

    public void deletePost(final Long id_Post) {
        final Post oldPost = postRepository.findById(id_Post).orElseThrow(() -> new RuntimeException("No data found!"));
        postRepository.delete(oldPost);
    }

    public List<Post> allPostofUser(final Long id_user) {

        final User user = userRepository.findById(id_user).orElse(null);
        return postRepository.findAllPostsPerUser(user);
    }
}

