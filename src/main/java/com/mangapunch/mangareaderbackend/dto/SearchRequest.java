package com.mangapunch.mangareaderbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String keywords;
    private int page;
    private String selectedGenres;
    private String unselectedGenres;
}
