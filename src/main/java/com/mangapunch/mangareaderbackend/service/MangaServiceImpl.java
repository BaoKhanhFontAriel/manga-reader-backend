package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Genre;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.repositories.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MangaServiceImpl implements MangaService {
    private static final int MANGA_PAGE_SIZE = 16;

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
        return mangaRepository.getAllMangaListByUpdate(page, MANGA_PAGE_SIZE);
    }



}
