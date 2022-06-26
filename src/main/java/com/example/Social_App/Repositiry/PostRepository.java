package com.example.Social_App.Repositiry;


import com.example.Social_App.Model.Post;
import com.example.Social_App.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("FROM Post p WHERE p.owner = :user")
    List<Post> findAllPostsPerUser(User user);
}
