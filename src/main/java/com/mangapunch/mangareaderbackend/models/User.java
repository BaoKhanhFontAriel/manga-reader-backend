package com.mangapunch.mangareaderbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "users_data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "fullname")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "uploader", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Chapter> uploadChapters;

    @JsonIgnore
    @ManyToMany(mappedBy = "userFavorites", cascade = {
            CascadeType.ALL }, fetch = FetchType.EAGER)
    private List<Manga> favoriteManga;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
