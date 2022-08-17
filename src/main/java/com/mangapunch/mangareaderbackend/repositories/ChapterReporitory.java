package com.mangapunch.mangareaderbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mangapunch.mangareaderbackend.models.Chapter;

@Repository
public interface ChapterReporitory extends JpaRepository<Chapter, Long>{

}