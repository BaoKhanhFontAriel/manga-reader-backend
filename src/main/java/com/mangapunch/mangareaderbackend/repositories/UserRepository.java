package com.mangapunch.mangareaderbackend.repositories;

import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findById(long id);

    @Query("select u from User u where u.username = :username or u.email = :email")
    User findByUsernameOrEmail(String username, String email);
    User findByUsername(String username);
    User findByEmail(String email);
}
