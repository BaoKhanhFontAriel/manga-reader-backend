package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.dto.SearchRequest;
import com.mangapunch.mangareaderbackend.dto.SearchResponse;
import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Genre;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.repositories.MangaRepository;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MangaServiceImpl implements MangaService {
    private static final int MANGA_PAGE_SIZE_4_X_4 = 16;
    private static final int MANGA_PAGE_SIZE_6_X_4 = 24;

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
        return mangaRepository.sortMangasByTotalViews();
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
        return mangaRepository.findTop5MangasByFavorite();
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
    public List<Manga> getAllMangaListByUpdate(int page) {
        return mangaRepository.getAllMangaListByUpdate(page, MANGA_PAGE_SIZE_4_X_4);
    }

    @Override
    public Manga findMangaByChapterId(long chapterId) {
        return mangaRepository.findMangaByChapterId(chapterId);
    }

    @Override
    public SearchResponse getSearchedMangas(SearchRequest searchRequest) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Manga.class)
                .get();

        // Search for mangas with title or author contain keywords
        Query authorAndTitleQuery = queryBuilder
                .phrase()
                .withSlop(5)
                .onField("title").andField("author")
                .sentence(searchRequest.getKeywords())
                .createQuery();

        Query selectedGenreQuery = queryBuilder
                .phrase()
                .onField("genres.value")
                .sentence(searchRequest.getSelectedGenres())
                .createQuery();

        Query unselectedGenreQuery = queryBuilder
                .phrase()
                .onField("genres.value")
                .sentence(searchRequest.getUnselectedGenres())
                .createQuery();

        Query query = null;

        if (searchRequest.getKeywords().isEmpty()) {
            if (searchRequest.getSelectedGenres().isEmpty()) {
                query = queryBuilder
                        .bool()
                        .should(authorAndTitleQuery)
                        .should(selectedGenreQuery)
                        .must(unselectedGenreQuery).not()
                        .createQuery();

            } else {
                query = queryBuilder
                        .bool()
                        .should(authorAndTitleQuery)
                        .must(selectedGenreQuery)
                        .must(unselectedGenreQuery).not()
                        .createQuery();
            }

        } else if (searchRequest.getSelectedGenres().isEmpty()) {
            query = queryBuilder
                    .bool()
                    .must(authorAndTitleQuery)
                    .should(selectedGenreQuery)
                    .must(unselectedGenreQuery).not()
                    .createQuery();

        } else {
            query = queryBuilder
                    .bool()
                    .must(authorAndTitleQuery)
                    .must(selectedGenreQuery)
                    .must(unselectedGenreQuery).not()
                    .createQuery();
        }

        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
                Manga.class);

        jpaQuery.setFirstResult(searchRequest.getPage()); // start from the 0th element
        jpaQuery.setMaxResults(MANGA_PAGE_SIZE_6_X_4); // return 16 elements
        // total pages of search results
        int totalPages = (int) Math.ceil(jpaQuery.getResultSize() / MANGA_PAGE_SIZE_6_X_4); 

        List<Manga> results = jpaQuery.getResultList();

        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setTotalPages(totalPages);
        searchResponse.setMangas(results);
        return searchResponse;
    }
}
