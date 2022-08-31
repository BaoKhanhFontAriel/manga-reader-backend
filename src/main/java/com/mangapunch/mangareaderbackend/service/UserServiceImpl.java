package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.models.Manga;
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

    @Autowired
    private MangaService mangaService;

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
    public void deleteUser(User user) {
        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(long id, User user) {
        User existedUser = userRepository.findById(id);
        if (existedUser != null) {
            existedUser.setName(user.getName());
            existedUser.setEmail(user.getEmail());
            existedUser.setUsername(user.getUsername());
            existedUser.setPassword(user.getPassword());
        }
        return existedUser;
    }

    @Override
    public List<Manga> addMangaToFavorite(long mangaid, long userid) {
        User user = userRepository.findById(userid);
        Manga manga = mangaService.findById(mangaid);
        user.getFavoriteManga().add(manga);
        manga.getUserFavorites().add(user);
        userRepository.saveAndFlush(user);
        return user.getFavoriteManga();
    }

    @Override
    public List<Manga> removeMangaToFavorite(long mangaid, long userid) {
        User user = userRepository.findById(userid);
        Manga manga = mangaService.findById(mangaid);
        user.getFavoriteManga().remove(manga);
        manga.getUserFavorites().remove(user);
        userRepository.saveAndFlush(user);
        return user.getFavoriteManga();
    };
}
