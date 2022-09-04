package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.dto.ChapterRequest;
import com.mangapunch.mangareaderbackend.models.Chapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ChapterService {
    List<Chapter> getAllChapters();

    void addChapter(Chapter chapter);

    Optional<Chapter> getChapterByid(Long chapterId);

    void deleteChapter(long chapterId);

    Chapter getLatestChapterByMangaId(long id);

    List<Chapter> findByMangaId(long mangaId);

    Chapter findChapterById(long id);

    Chapter addNewChapter(long mangaid, ChapterRequest chapterRequest);

    Chapter updateChapterDetail(long chapterid, ChapterRequest chapterRequest);

    String[] getPagesByChapterId(long id);

    List<Chapter> getAllChapterByUsername(String username);

}
