package com.mangapunch.mangareaderbackend.service;

import java.util.List;

import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.repositories.ChapterReporitory;

public class ChapterServiceImpl implements ChapterService{
    private ChapterReporitory  chapterRepository;

    @Override
    public List<Chapter> getAllChapter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Chapter createChapter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Chapter getChapterByid(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Chapter deleteChapter(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Chapter updateChapter(Long is) {
        // TODO Auto-generated method stub
        return null;
    }

    private int totalViews = 0;
    public int getTotalViews(){
        chapterRepository.findAll().stream().forEach((chapter) -> totalViews += chapter.getViews());
        return totalViews;
    }
}
