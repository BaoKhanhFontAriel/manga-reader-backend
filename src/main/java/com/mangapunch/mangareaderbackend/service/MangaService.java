package com.mangapunch.mangareaderbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mangapunch.mangareaderbackend.models.Manga;

@Service
public interface MangaService {
    List<Manga> getAllMangas();
    void addManga(Manga manga);
    Optional<Manga> getMangaByid(Long mangaId);
    void deleteManga(Long mangaId);
}
