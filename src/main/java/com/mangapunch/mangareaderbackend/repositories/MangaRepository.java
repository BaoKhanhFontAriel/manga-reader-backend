package com.mangapunch.mangareaderbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mangapunch.mangareaderbackend.models.Manga;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Long>{
    
}
