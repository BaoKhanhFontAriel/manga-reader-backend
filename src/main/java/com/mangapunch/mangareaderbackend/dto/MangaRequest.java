package com.mangapunch.mangareaderbackend.dto;

import java.util.List;

import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Genre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MangaRequest {
    private String title;
    private String thumbnailUrl;
    private String author;
    private List<Genre> genres;
    private String summary;
}
