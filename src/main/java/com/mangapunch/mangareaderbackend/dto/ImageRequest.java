package com.mangapunch.mangareaderbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest {
    private MultipartFile file;
    private String username;
}
