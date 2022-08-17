package com.mangapunch.mangareaderbackend.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.mangapunch.mangareaderbackend.service.ChapterService;
import com.mangapunch.mangareaderbackend.service.ChapterServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "manga_data")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "thumbnailUrl")
    private String thumbnailUrl;

    @Column(name = "author")
    private String author;

    @ManyToMany
    @JoinTable(name = "manga_genre_data", joinColumns = @JoinColumn(name = "manga_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(name = "favorite_data", joinColumns = @JoinColumn(name = "manga_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Manga> favorites;

    @Column(name = "summary")
    private String summary;

    @OneToMany(mappedBy = "manga")
    private List<Chapter> chapters;



    // @Transient
    // private int views;
    // public int getViews(){
    //     return chapterService.getTotalViews();
    // }

    // public Manga(long id, String title, String thumbnailUrl, String author, List<Genre> genres, String summary) {
    //     this.id = id;
    //     this.title = title;
    //     this.thumbnailUrl = thumbnailUrl;
    //     this.author = author;
    //     this.genres = genres;
    //     this.summary = summary;
    //     this.chapters = new ArrayList<>();
    // }
}
