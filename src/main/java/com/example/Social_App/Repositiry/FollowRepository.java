package com.example.Social_App.Repositiry;

import com.example.Social_App.Model.Follow;
import com.example.Social_App.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.this_One = :User1 AND f.to_This = :User2")
    int numberOfFollow(User User1, User User2);
}
