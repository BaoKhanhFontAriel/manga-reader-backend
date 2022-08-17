package com.mangapunch.mangareaderbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.repositories.MangaRepository;

@Component
public class MangaServiceIml implements MangaService{
    @Autowired
    private MangaRepository mangaRepository;

    @Override
    public List<Manga> getAllManga() {
        // TODO Auto-generated method stub
        return mangaRepository.findAll();
    }

    @Override
    public Manga createManga() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Manga getMangaByid(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Manga deleteManga(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Manga updateManga(Long is) {
        // TODO Auto-generated method stub
        return null;
    }
}
