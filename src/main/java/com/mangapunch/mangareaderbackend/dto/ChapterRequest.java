package com.mangapunch.mangareaderbackend.dto;

import javax.validation.constraints.NotBlank;

import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChapterRequest {
    @NotBlank(message= "{name.notblank}")
    private String title;

    @NotBlank(message= "{name.notblank}")
    private String pageUrls;

    private Manga manga;

    private User uploader;
}
