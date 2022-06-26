package com.example.Social_App.Repositiry;


import com.example.Social_App.Model.Comment;
import com.example.Social_App.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("FROM Comment c WHERE c.post = :post")
    List<Comment> findAllCommentPerPost(Post post);
}
