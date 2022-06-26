package com.example.Social_App.Repositiry;


import com.example.Social_App.Model.Comment;
import com.example.Social_App.Model.Post;
import com.example.Social_App.Model.React;
import com.example.Social_App.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactRepository extends JpaRepository<React, Long> {

    @Query("FROM React r WHERE r.post = :post")
    List<React> findAllReactPerPost(Post post);

    @Query("FROM React c WHERE c.comment = :comment")
    List<React> findAllReactPerComment(Comment comment);


    @Query("SELECT c.post FROM React c WHERE c.owner = :user AND c.comment = NULL")
    List<Post> findAllReactOfUserPerPost(User user);

    @Query("SELECT COUNT(c) FROM React c WHERE c.post = :post")
    public int countPostReacts(Post post);


}
