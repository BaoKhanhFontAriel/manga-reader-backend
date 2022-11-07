package com.mangapunch.mangareaderbackend.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChapterRequest {
    @NotBlank(message = "{name.notblank}")
    private String title;

    private String optional;

    private long mangaid;

    @NotBlank(message = "{name.notblank}")
    private String pageUrls;

    private long uploaderid;
}
