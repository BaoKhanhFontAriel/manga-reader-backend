package com.mangapunch.mangareaderbackend.dto;

import java.util.List;

import com.mangapunch.mangareaderbackend.models.Manga;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MangaResponse {
    private int totalPages;
    private List<Manga> mangas;
}
