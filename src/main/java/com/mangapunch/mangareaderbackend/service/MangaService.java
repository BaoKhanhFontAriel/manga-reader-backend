package com.mangapunch.mangareaderbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mangapunch.mangareaderbackend.models.Manga;

@Service
public interface MangaService {
    List<Manga> getAllManga();
    Manga createManga();
    Manga getMangaByid(Long id);
    Manga deleteManga(Long id);
    Manga updateManga(Long is);
}
