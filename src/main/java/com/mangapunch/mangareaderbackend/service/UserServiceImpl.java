package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByid(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        // TODO Auto-generated method stub
        return userRepository.findByUsernameOrEmail(username, email);
    }
}
