package com.mangapunch.mangareaderbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mangapunch.mangareaderbackend.models.Genre;


@Service
public interface GenreService {
    List<String> getAllGenres();
}
