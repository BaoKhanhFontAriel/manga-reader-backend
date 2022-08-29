package com.mangapunch.mangareaderbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users_data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "fullname")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "uploader")
    private List<Chapter> uploadChapters;

    @JsonIgnore
    @ManyToMany(mappedBy = "userFavorites")
    private List<Manga> favoriteManga;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
