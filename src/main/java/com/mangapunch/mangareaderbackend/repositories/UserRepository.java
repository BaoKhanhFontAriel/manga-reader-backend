package com.mangapunch.mangareaderbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mangapunch.mangareaderbackend.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsernameOrEmail(String username, String email);
}
