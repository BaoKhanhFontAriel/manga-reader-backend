package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.dto.UserEditRequest;
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

    User editUser(User currentUser, UserEditRequest request) throws Exception;

    void deleteUser(User user);

    User findByUsernameOrEmail(String username, String email);

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(long id);

    List<Manga> addMangaToFavorite(long mangaid, User currentUser);

    List<Manga> removeMangaToFavorite(long mangaid, User currentUser);

    boolean isMangaFavoritedByUser(long mangaid, User currentUser);

    List<Manga> getFavoriteMangaByUserId(User currentUser);

    boolean isPasswordMatch(User currentUser, String rawPassword);

    void editPassword(User currentUser, String newPassword);
}
