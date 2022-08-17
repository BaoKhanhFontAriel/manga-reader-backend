package com.mangapunch.mangareaderbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mangapunch.mangareaderbackend.models.Chapter;

@Service
public interface ChapterService{
    List<Chapter> getAllChapters();
    void addChapter(Chapter chapter);
    Optional<Chapter> getChapterByid(Long chapterId);
    void deleteChapter(Long chapterId);
}
