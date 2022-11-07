package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.dto.MangaResponse;
import com.mangapunch.mangareaderbackend.dto.SearchRequest;
import com.mangapunch.mangareaderbackend.dto.SearchResponse;
import com.mangapunch.mangareaderbackend.models.Manga;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MangaService {
    void addManga(Manga manga);

    Optional<Manga> getMangaById(Long mangaId);

    void deleteManga(Long mangaId);

    List<Manga> findTop5MangasWithMostViews();

    long getTotalViewsByMangaId(long mangaId);

    int getNumberOfFavoriteByMangaId(long mangaId);

    List<Manga> findTop5MangasByFavorite();

    Manga findById(long mangaid);

    List<String> getGenresByMangaId(long mangaid);

    String getUpdateDateTimeByMangaId(long mangaid);

    MangaResponse getAllMangaListByUpdate(int page);

    Manga findMangaByChapterId(long chapterId);

    MangaResponse findMangaListByGenreSortBy(String genre, String sortBy, int page);


}
