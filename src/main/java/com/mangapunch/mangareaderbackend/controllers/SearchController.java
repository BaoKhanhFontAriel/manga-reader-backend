package com.mangapunch.mangareaderbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mangapunch.mangareaderbackend.dto.SearchResponse;
import com.mangapunch.mangareaderbackend.service.SearchService;

@RestController
@RequestMapping("/api/search/")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/search-full")
    public ResponseEntity<?> getSearchedMangas(@RequestParam String keywords, @RequestParam int page,
            @RequestParam String selectedGenres, @RequestParam String unSelectedGenres) {
        SearchResponse result = searchService.getSearchedMangas(keywords, page,
                selectedGenres, unSelectedGenres);
        try {
            if (result.getMangas().isEmpty()) {
                throw new Exception("Không tìm thấy kết quả");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/search-quick")
    public ResponseEntity<SearchResponse> getQuickSearchedMangas(String keywords) {
        SearchResponse result = searchService.getQuickSearchedMangas(keywords);
        try {
            if (result.getMangas().isEmpty()) {
                throw new Exception("Không tìm thấy kết quả");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
