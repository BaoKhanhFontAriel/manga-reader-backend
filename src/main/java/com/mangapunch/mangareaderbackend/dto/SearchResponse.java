package com.mangapunch.mangareaderbackend.dto;

import java.util.List;

import com.mangapunch.mangareaderbackend.models.Manga;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private int totalPages;
    private List<Manga> mangas;
}
