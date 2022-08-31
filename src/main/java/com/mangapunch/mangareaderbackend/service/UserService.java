package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getAllUsers();

    void addUser(User User);

    Optional<User> getUserByid(Long UserId);

    void deleteUser(User user);

    User findByUsernameOrEmail(String username, String email);

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(long id);

    User updateUser(long id, User user);

    List<Manga> addMangaToFavorite(long mangaid, String username);

    List<Manga> removeMangaToFavorite(long mangaid, String username);

    boolean isMangaFavoritedByUser(long mangaid, String username);

    List<Manga> getFavoriteMangaByUsername(String username);

}
