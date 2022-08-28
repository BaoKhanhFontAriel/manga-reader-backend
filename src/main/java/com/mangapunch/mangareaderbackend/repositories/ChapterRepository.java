package com.mangapunch.mangareaderbackend.repositories;

import com.mangapunch.mangareaderbackend.models.Chapter;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    // find the latest chapter of the desired manga
    @Query(value = "SELECT * FROM chapter_data WHERE mangaid = :id ORDER BY uploaddate DESC, uploadtime DESC LIMIT 1", nativeQuery = true)
    Chapter findLatestChapterByMangaId(long id);

    List<Chapter> findByMangaIdOrderByTitleDesc(long mangaId);
}
