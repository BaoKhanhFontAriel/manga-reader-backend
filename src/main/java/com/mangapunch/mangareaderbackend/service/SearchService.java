package com.mangapunch.mangareaderbackend.service;

import org.springframework.stereotype.Service;

import com.mangapunch.mangareaderbackend.dto.SearchResponse;

@Service
public interface SearchService {
    SearchResponse getSearchedMangas(String keywords, int page,
            String selectedGenres, String unselectedGenres);

    SearchResponse getQuickSearchedMangas(String keywords);
}
