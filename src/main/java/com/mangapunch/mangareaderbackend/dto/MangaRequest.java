package com.mangapunch.mangareaderbackend.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Genre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MangaRequest {
    @NotBlank(message= "{name.notblank}")
    private String title;
    @NotBlank(message= "{name.notblank}")
    private String thumbnailUrl;
    @NotBlank(message= "{name.notblank}")
    private String author;
    // private List<Genre> genres;
    @NotBlank(message= "{name.notblank}")
    private String summary;
}
