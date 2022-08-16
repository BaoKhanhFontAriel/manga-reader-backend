package com.mangapunch.mangareaderbackend.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String title;
    private String thumbnailUrl;
    private String author;
    private List<Genre> genres;
    private int views = 0;
    private List<User> favorites;
    private String summary;
    private List<Chapter> chapters;
    
    // public Manga(String id, String title, String thumbnailUrl, String author, List<Genre> genres, String summary,
    //         List<Chapter> chapters) {
    //     this.id = id;
    //     this.title = title;
    //     this.thumbnailUrl = thumbnailUrl;
    //     this.author = author;
    //     this.genres = genres;
    //     this.summary = summary;
    //     this.chapters = chapters;
        
    //     chapters.stream().forEach((chapter) ->  this.views += chapter.getViews());

        
    // }

    
}
