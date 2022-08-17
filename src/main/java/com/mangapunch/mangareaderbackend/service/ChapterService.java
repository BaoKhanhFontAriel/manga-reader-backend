package com.mangapunch.mangareaderbackend.service;

import java.util.List;

import com.mangapunch.mangareaderbackend.models.Chapter;

public interface ChapterService{
    List<Chapter> getAllChapter();
    Chapter createChapter();
    Chapter getChapterByid(Long id);
    Chapter deleteChapter(Long id);
    Chapter updateChapter(Long is);
}
