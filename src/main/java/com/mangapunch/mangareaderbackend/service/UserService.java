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
}
