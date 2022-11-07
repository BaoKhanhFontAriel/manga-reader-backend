package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.dto.UserEditRequest;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.repositories.MangaRepository;
import com.mangapunch.mangareaderbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MangaRepository mangaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager em;

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
    @Transactional
    public List<Manga> addMangaToFavorite(long mangaid, User user) {
        try {
            Manga manga = mangaService.findById(mangaid);
            user.getFavoriteManga().add(manga);
            manga.getUserFavorites().add(user);
            userRepository.save(user);
            return user.getFavoriteManga();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @Override
    @Transactional
    public List<Manga> removeMangaToFavorite(long mangaid, User user) {
        try {
            Manga manga = mangaService.findById(mangaid);
            user.getFavoriteManga().removeIf((fManga) -> fManga.getId() == mangaid);
            manga.getUserFavorites().removeIf((userF) -> userF.getId() == user.getId());
            userRepository.saveAndFlush(user);
            return user.getFavoriteManga();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    };

    // check if a user has favorited a manga,
    // query return 0 meaning manga is not favorited, return 1 meaning manga is
    // favorited
    @Override
    public boolean isMangaFavoritedByUser(long mangaid, User user) {
        return userRepository.isMangaFavoritedByUser(mangaid, user.getId()) == 1;
    };

    @Override
    public List<Manga> getFavoriteMangaByUserId(User user) {
        return userRepository.getFavoriteMangaByUserId(user.getId());
    }

    @Override
    public User editUser(User user, UserEditRequest request) throws Exception {


            if (!request.getFullname().isEmpty()) {
                user.setName(request.getFullname());
            }
            if (!request.getEmail().isEmpty()) {
                User existed = userRepository.findByEmail(request.getEmail());
                if (existed != null) {
                    throw new Exception("Email đã được sử dụng! Vui lòng nhập email khác!");
                }
                user.setEmail(request.getEmail());
            }
            if (!request.getUsername().isEmpty()) {
                User existed = userRepository.findByUsername(request.getUsername());
                if (existed != null) {
                    throw new Exception("Tên đăng nhập đã được sử dụng! Vui lòng nhập tên đăng nhập khác!");
                }
                user.setUsername(request.getUsername());
            }
            
            userRepository.save(user);
            return user;
            // } else {
            // return null;
            // }
        // } catch (Exception e) {
        //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        // }

    }

    @Override
    public boolean isPasswordMatch(User user, String rawPassword) {
        String encodedPassword = user.getPassword();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public void editPassword(User currentUser, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        currentUser.setPassword(encodedPassword);
        userRepository.save(currentUser);
    }
}
