package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.models.Chapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ChapterService{
    List<Chapter> getAllChapters();
    void addChapter(Chapter chapter);
    Optional<Chapter> getChapterByid(Long chapterId);
    void deleteChapter(Long chapterId);
    Chapter getLatestChapterByMangaId(long id);
    List<Chapter> findByMangaId(long mangaId);
    Chapter findChapterById(long id); 
}
