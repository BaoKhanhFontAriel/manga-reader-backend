package com.mangapunch.mangareaderbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Indexed
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genre_data")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private GenreEnum genreNum;

    @Field(name="value", store= Store.YES)
    private String getGenreValue(){
        return genreNum.getValue();
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    @ContainedIn
    private List<Manga> mangas;
}
