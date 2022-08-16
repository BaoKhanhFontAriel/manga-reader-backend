package com.mangapunch.mangareaderbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mangapunch.mangareaderbackend.repositories.MangaRepository;

@RestController
public class MangaController {
    private MangaRepository mangaRepository;

    @Autowired
    public MangaController(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    
}
