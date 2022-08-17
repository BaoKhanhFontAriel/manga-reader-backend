package com.mangapunch.mangareaderbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.repositories.MangaRepository;

@Component
public class MangaServiceIml implements MangaService {
    @Autowired
    private MangaRepository mangaRepository;

    @Override
    public List<Manga> getAllMangas() {
        return mangaRepository.findAll();
    }

    @Override
    public void addManga(Manga manga) {
        mangaRepository.save(manga);
    }

    @Override
    public Optional<Manga> getMangaByid(Long mangaId) {
        return mangaRepository.findById(mangaId);
    }

    @Override
    public void deleteManga(Long mangaId) {
        mangaRepository.deleteById(mangaId);

    }
}
