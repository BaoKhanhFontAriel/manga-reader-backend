package com.mangapunch.mangareaderbackend.repositories;

import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Genre;
import com.mangapunch.mangareaderbackend.models.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Long> {

        // sort manga with total views in desc order with total views are sum of all
        // chapters' views
        @Query(value = "SELECT * FROM manga_data \n" +
                        "RIGHT JOIN chapter_data \n" +
                        "ON manga_data.id = chapter_data.mangaid \n" +
                        "GROUP BY mangaid \n" +
                        "ORDER BY SUM(chapter_data.views) \n" +
                        "DESC LIMIT 5 ", nativeQuery = true)
        List<Manga> sortMangasByTotalViews();

        // get sum of all chapters' views for a specified manga
        @Query(value = "SELECT SUM(chapter_data.views) FROM manga_data \n" +
                        "JOIN chapter_data \n" +
                        "ON manga_data.id = chapter_data.mangaid AND mangaid = :mangaId\n" +
                        "GROUP BY mangaid", nativeQuery = true)
        long getTotalViewsByMangaId(long mangaId);

        // get 5 manga with the highest user favorite
        @Query(value = "SELECT * FROM manga_data \n" +
                        "JOIN favorite_data ON manga_data.id = favorite_data.manga_id  \n" +
                        "JOIN users_data ON users_data.id = favorite_data.user_id  \n" +
                        "GROUP BY manga_data.id \n" +
                        "ORDER BY COUNT(DISTINCT users_data.id)\n" +
                        "DESC LIMIT 5", nativeQuery = true)
        List<Manga> findTop5MangasByFavorite();

        // get number of favorites for a specified manga
        @Query("select count(u) from Manga m join m.userFavorites u where m.id = :mangaId")
        int getNumberOfFavoriteByMangaId(long mangaId);

        // sort mangas distinct by which has the newest chapter uploaded latest
        @Query(value = "SELECT * FROM manga_data m\n" +
                        "INNER JOIN \n" +
                        "(SELECT *, ROW_NUMBER() OVER (PARTITION BY mangaid ORDER BY uploaddate DESC, uploadtime DESC) RN "
                        +
                        "FROM chapter_data) c\n" +
                        "ON m.id = c.mangaid AND RN = 1\n" +
                        "ORDER BY uploaddate DESC, uploadtime DESC " +
                        "LIMIT :page, :pageSize", nativeQuery = true)
        List<Manga> getAllMangaListByUpdate(int page, int pageSize);

        // find manga by id
        Manga findById(long mangaid);

        // get all genres of a manga
        @Query("SELECT m.genres FROM Manga m where m.id = :mangaid")
        List<Genre> getGenresByMangaId(long mangaid);

        @Query("select m.chapters from Manga m where m.id = :mangaid")
        List<Chapter> getAllChaptersByMangaId(long mangaid);


}