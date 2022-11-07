package com.mangapunch.mangareaderbackend.repositories;

import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Genre;
import com.mangapunch.mangareaderbackend.models.GenreEnum;
import com.mangapunch.mangareaderbackend.models.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Long> {

        // get sum of all chapters' views for a specified manga
        @Query("select sum(c.views) from Manga m join m.chapters c where m.id = :mangaId group by m.id ")
        long getTotalViewsByMangaId(long mangaId);

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

        @Query("select m from Manga m join m.chapters c where c.id = :chapterId")
        Manga findMangaByChapterId(long chapterId);

        // get number of mangas having chapters
        @Query("select count(distinct m) from Manga m join m.chapters c")
        int countMangasWithChapters();

        // sort mangas distinct by which has the newest chapter uploaded latest
        @Query(value = "SELECT m.id," +
                                "m.title, " +
                                "m.author, " +
                                "m.summary, " +
                                "m.thumbnailurl " +
                        "FROM manga_data m\n" +
                        "INNER JOIN \n" +
                                "(SELECT id, " +
                                "mangaid, " +
                                "uploaddate, " +
                                "uploadtime, " +
                                "ROW_NUMBER() OVER (PARTITION BY mangaid ORDER BY uploaddate DESC, uploadtime DESC) RN " +
                                "FROM chapter_data) c\n" +
                        "ON m.id = c.mangaid AND RN = 1\n" +
                        " JOIN manga_genres_data mg ON mg.manga_id = m.id " +
                        " JOIN genre_data g on g.id  = mg.genre_id and (g.genre = :genre or :genre is null) " +
                        "group by m.id " +
                        "ORDER BY any_value(c.uploaddate) DESC, any_value(c.uploadtime) DESC " +
                        "LIMIT :page, :pageSize", nativeQuery = true)
        List<Manga> getMangaListByGenreSortByUpdate(String genre, int page, int pageSize);

        @Query(value = "SELECT m.id," +
                                "m.title, " +
                                "m.author, " +
                                "m.summary, " +
                                "m.thumbnailurl " +
                        "FROM manga_data m\n" +
                        "join chapter_data c on c.mangaid = m.id " +
                        " join manga_genres_data mg on  mg.manga_id = m.id " +
                        " join genre_data g on g.id = mg.genre_id and (g.genre = :genre or :genre is null) " +
                        "group by m.id " +
                        "order by sum(c.views) desc " +
                        "limit :page, :pageSize ", nativeQuery = true)
        List<Manga> getMangaListByGenreSortByTopView(String genre, int page, int pageSize);

        @Query(value = "SELECT m.id," +
                                "m.title, " +
                                "m.author, " +
                                "m.summary, " +
                                "m.thumbnailurl " +
                        "FROM manga_data m\n" +
                        "join favorite_data f on  f.manga_id = m.id " +
                        "join users_data u on u.id = f.user_id " +
                        "join manga_genres_data mg on  mg.manga_id = m.id " +
                        "join genre_data g on g.id = mg.genre_id and (g.genre = :genre or :genre is null) " +
                        "group by m.id " +
                        "order by count(u.id) desc " +
                        "limit :page, :pageSize ", nativeQuery = true)
        List<Manga> getMangaListByGenreSortByTopFavorite(String genre, int page, int pageSize);

        // get number of mangas by selected genre
        @Query("select count(distinct m) from Manga m join m.genres g where g.genreNum = :genre or :genre is null")
        int countMangasByGenre(GenreEnum genre);

}