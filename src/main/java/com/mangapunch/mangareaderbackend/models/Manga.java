package com.mangapunch.mangareaderbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "thumbnailurl")
    private String thumbnailUrl;

    @Column(name = "author")
    private String author;

    @JsonIgnore
    @ManyToMany()
    @JoinTable(name = "manga_genres_data", joinColumns = @JoinColumn(name = "manga_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @JsonIgnore
    @ManyToMany()
    @JoinTable(name = "favorite_data", joinColumns = @JoinColumn(name = "manga_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userFavorites;

    @Column(name = "summary")
    private String summary;

    @JsonIgnore
    @OneToMany(mappedBy = "manga")
    private List<Chapter> chapters;
}
