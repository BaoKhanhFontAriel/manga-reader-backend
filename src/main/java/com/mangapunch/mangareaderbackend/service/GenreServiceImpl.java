package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.models.Genre;
import com.mangapunch.mangareaderbackend.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<String> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        try {
            if (!genres.isEmpty()) {
                List<String> genreValues = new ArrayList<>();
                genres.stream().forEach((genre) -> {
                    genreValues.add(genre.getGenreNum().getValue());
                });
                return genreValues;
            } else {
                throw new Exception("Không có dữ liệu về thể loại");
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
