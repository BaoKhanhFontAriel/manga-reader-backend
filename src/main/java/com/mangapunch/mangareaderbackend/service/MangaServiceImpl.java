package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.dto.MangaResponse;
import com.mangapunch.mangareaderbackend.dto.SearchResponse;
import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Genre;
import com.mangapunch.mangareaderbackend.models.GenreEnum;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.MangaPageSize;
import com.mangapunch.mangareaderbackend.repositories.GenreRepository;
import com.mangapunch.mangareaderbackend.repositories.MangaRepository;
import com.mangapunch.mangareaderbackend.utils.GenreUtil;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MangaServiceImpl implements MangaService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private MangaRepository mangaRepository;

    @Override
    public void addManga(Manga manga) {
        mangaRepository.save(manga);
    }

    @Override
    public Optional<Manga> getMangaById(Long mangaId) {
        return mangaRepository.findById(mangaId);
    }

    @Override
    public void deleteManga(Long mangaId) {
        mangaRepository.deleteById(mangaId);
    }

    @Override
    public List<Manga> findTop5MangasWithMostViews() {
        javax.persistence.Query query = entityManager
                .createQuery("select m from Manga m join m.chapters c group by m.id order by sum(c.views) desc");
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    public long getTotalViewsByMangaId(long mangaId) {
        return mangaRepository.getTotalViewsByMangaId(mangaId);
    }

    @Override
    public int getNumberOfFavoriteByMangaId(long mangaId) {
        return mangaRepository.getNumberOfFavoriteByMangaId(mangaId);
    }

    @Override
    public List<Manga> findTop5MangasByFavorite() {
        javax.persistence.Query query = entityManager
                .createQuery("select m from Manga m join m.userFavorites u group by m.id order by count(u) desc");
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    public Manga findById(long mangaid) {
        return mangaRepository.findById(mangaid);
    }

    @Override
    public List<String> getGenresByMangaId(long mangaid) {
        List<String> genreValues = new ArrayList<>();
        List<Genre> genres = mangaRepository.getGenresByMangaId(mangaid);

        genres.stream().forEach(g -> genreValues.add(g.getGenreNum().getValue()));
        return genreValues;
    }

    @Override
    public String getUpdateDateTimeByMangaId(long mangaid) {
        Chapter latestChapter = chapterService.getLatestChapterByMangaId(mangaid);
        String date = latestChapter.getUploadedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String time = latestChapter.getUploadedTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        return time + " " + date;
    }

    @Override
    public MangaResponse getAllMangaListByUpdate(int page) {

        // convert from page appear on frontend to the real page in repository
        int repositoryPage = page * MangaPageSize.MANGA_PAGE_SIZE_4_X_4;
        List<Manga> mangas = mangaRepository.getAllMangaListByUpdate(repositoryPage,
                MangaPageSize.MANGA_PAGE_SIZE_4_X_4);
        int totalPages = (int) Math.ceil((double) mangaRepository.countMangasWithChapters() / MangaPageSize.MANGA_PAGE_SIZE_4_X_4);
        MangaResponse mangaResponse = new MangaResponse(totalPages, mangas);
        return mangaResponse;
    }

    @Override
    public Manga findMangaByChapterId(long chapterId) {
        return mangaRepository.findMangaByChapterId(chapterId);
    }

    @Override
    public MangaResponse findMangaListByGenreSortBy(String genre, String sortBy, int page) {

        try {
            GenreEnum selectGenre = GenreUtil.convertStringToGenreEnum(genre);
            String genreName = selectGenre == null ? null : selectGenre.name();
            int repositoryPage = page * MangaPageSize.MANGA_PAGE_SIZE_4_X_4;

            List<Manga> mangas = new ArrayList<>();
            switch (sortBy) {
                case "update":
                    mangas = mangaRepository.getMangaListByGenreSortByUpdate(genreName, repositoryPage,
                    MangaPageSize.MANGA_PAGE_SIZE_4_X_4);
                    break;
                case "top-view":
                    mangas = mangaRepository.getMangaListByGenreSortByTopView(genreName, repositoryPage,
                    MangaPageSize.MANGA_PAGE_SIZE_4_X_4);
                    break;
                case "top-favorite":
                    mangas = mangaRepository.getMangaListByGenreSortByTopFavorite(genreName, repositoryPage,
                    MangaPageSize.MANGA_PAGE_SIZE_4_X_4);
                    break;
                default:
                    break;
            }

            int totalPages = (int) Math
                    .ceil((double) mangaRepository.countMangasByGenre(selectGenre) / MangaPageSize.MANGA_PAGE_SIZE_4_X_4);
            MangaResponse mangaResponse = new MangaResponse(totalPages, mangas);
            return mangaResponse;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            // e.printStackTrace();
        }
    }

}
