package com.mangapunch.mangareaderbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.repositories.ChapterRepository;

public class ChapterServiceImpl implements ChapterService{
    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }

    @Override
    public void addChapter(Chapter chapter) {
        chapterRepository.save(chapter);
    }

    @Override
    public Optional<Chapter> getChapterByid(Long chapterId) {
        return chapterRepository.findById(chapterId);
    }

    @Override
    public void deleteChapter(Long chapterId) {
        chapterRepository.deleteById(chapterId);
    }
}
