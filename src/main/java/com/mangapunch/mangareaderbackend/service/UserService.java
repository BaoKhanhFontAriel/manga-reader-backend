package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getAllUsers();
    void addUser(User User);
    Optional<User> getUserByid(Long UserId);
    void deleteUser(Long UserId);
    User findByUsernameOrEmail(String username, String email);
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(long id);

}
