package com.mangapunch.mangareaderbackend.repositories;

import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    @Query("select u from User u where u.username = :username or u.email = :email")
    User findByUsernameOrEmail(String username, String email);

    User findByUsername(String username);

    User findByEmail(String email);

    @Query("select u.favoriteManga from User u where u.username = :username")
    List<Manga> getFavoriteMangaByUser(String username);

    // check if a user has favorited a manga,
    // query return 0 meaning manga is not favorited, return 1 meaning manga is
    // favorited
    @Query("select count(m) from Manga m join m.userFavorites u " +
            "where m.id = :mangaid and u.username = :username")
    int isMangaFavoritedByUser(long mangaid, String username);
}
