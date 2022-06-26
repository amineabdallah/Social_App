package com.example.Social_App.Repositiry;


import com.example.Social_App.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User c WHERE c.username = :usernam")
    User findByUsername(String usernam);

    @Query("FROM User c WHERE c.email = :email")
    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);

}
