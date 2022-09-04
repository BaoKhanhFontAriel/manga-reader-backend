package com.mangapunch.mangareaderbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Indexed
@Builder
@Table(name = "manga_data")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "title", unique = true)
    @Field
    private String title;

    @Column(name = "thumbnailurl")
    private String thumbnailUrl;

    @Column(name = "author")
    @Field
    private String author;

    @JsonIgnore
    @ManyToMany()
    @JoinTable(name = "manga_genres_data", joinColumns = @JoinColumn(name = "manga_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @IndexedEmbedded
    private List<Genre> genres;

    @JsonIgnore
    @ManyToMany()
    @JoinTable(name = "favorite_data", joinColumns = @JoinColumn(name = "manga_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userFavorites;

    @Column(name = "summary")
    private String summary;

    @JsonIgnore
    @OneToMany(mappedBy = "manga", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Chapter> chapters;
}
