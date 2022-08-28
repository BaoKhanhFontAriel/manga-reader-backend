package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.repositories.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ChapterServiceImpl implements ChapterService {
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

    @Override
    public Chapter getLatestChapterByMangaId(long id) {
        return chapterRepository.findLatestChapterByMangaId(id);
    }

    @Override
    public List<Chapter> findByMangaId(long mangaId) {
        return chapterRepository.findByMangaIdOrderByTitleDesc(mangaId);
    }

    @Override
    public Chapter findChapterById(long id) {
        Chapter chapter = chapterRepository.findById(id).get();

        return chapter;
    }
}
