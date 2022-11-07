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

    // sort chapters belong to a manga by title
    @Query("select c from Chapter c where c.manga.id = :mangaId order by substring(c.title, 9) * 1 desc")
    List<Chapter> findByMangaIdOrderByTitleDesc(long mangaId);

    @Query("select c from User u join u.uploadChapters c where u.id = :userId order by c.uploadedDate desc, c.uploadedTime desc")
    List<Chapter> findByUserIdOderByDateTime(long userId);
}
