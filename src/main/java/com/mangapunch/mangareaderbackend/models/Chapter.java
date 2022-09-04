package com.mangapunch.mangareaderbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.search.annotations.Indexed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.BooleanSupplier;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Indexed
@Table(name = "chapter_data")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "optional")
    private String optional;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mangaid")
    private Manga manga;

    @Column(name = "pageurls")
    private String pageUrls;

    @Column(name = "views")
    private int views;

    @Column(name = "uploaddate", columnDefinition = "DATE")
    private LocalDate uploadedDate;

    @Column(name = "uploadtime", columnDefinition = "TIME")
    private LocalTime uploadedTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "uploaderid")
    private User uploader;

    public BooleanSupplier isNotNull() {
        return null;
    }
}
