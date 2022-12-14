package com.mangapunch.mangareaderbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mangapunch.mangareaderbackend.models.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>{
    
}
