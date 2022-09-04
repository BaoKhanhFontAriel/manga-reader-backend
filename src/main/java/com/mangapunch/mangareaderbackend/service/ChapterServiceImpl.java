package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.dto.ChapterRequest;
import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.repositories.ChapterRepository;
import com.mangapunch.mangareaderbackend.repositories.MangaRepository;
import com.mangapunch.mangareaderbackend.repositories.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MangaRepository mangaRepository;

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
    public void deleteChapter(long chapterId) {
        Optional<Chapter> chapter = chapterRepository.findById(chapterId);
        try {
            if (chapter.isPresent()) {                
                chapterRepository.deleteById(chapterId);
                // chapterRepository.flush();
            }
            else {
                throw new Exception("Truyện không tồn tại!");
            }
        } catch (Exception e) {
            return;
        }
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

    @Override
    public String[] getPagesByChapterId(long id) {
        Optional<Chapter> chapter = chapterRepository.findById(id);
        String[] pages = { "" };
        try {
            if (chapter.isPresent()) {
                pages = chapter.get().getPageUrls().split("[ \r\n]+");
                return pages;
            } else
                throw new Exception("Chương truyện không tồn tại!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pages;
    }

    @Override
    public Chapter addNewChapter(long mangaid, ChapterRequest chapterRequest) {
        User uploader = userRepository.findByUsername(chapterRequest.getUsername());
        Manga manga = mangaRepository.findById(mangaid);

        Chapter chapter = Chapter.builder()
                .title(chapterRequest.getTitle())
                .optional(chapterRequest.getOptional())
                .pageUrls(chapterRequest.getPageUrls())
                .views(0)
                .uploadedDate(LocalDate.now())
                .uploadedTime(LocalTime.now())
                .build();

        chapter.setManga(manga);
        chapter.setUploader(uploader);

        chapterRepository.save(chapter);
        chapterRepository.flush();

        return chapter;
    }

    @Override
    public List<Chapter> getAllChapterByUsername(String username) {

        return chapterRepository.findByUsernameOderByDateTime(username);
    }

    @Override
    public Chapter updateChapterDetail(long chapterid, ChapterRequest chapterRequest) {
        Optional<Chapter> chapter = chapterRepository.findById(chapterid);
        try {
            if (chapter.isPresent()) {
                try {
                    Chapter edited = chapter.get();
                    edited.setTitle(chapterRequest.getTitle());
                    edited.setOptional(chapterRequest.getOptional());
                    edited.setPageUrls(chapterRequest.getPageUrls());
                    chapterRepository.saveAndFlush(edited);
                    return edited;
                } catch (Exception e) {
                    throw new Exception("Cập nhật thất bại do chuyển đổi dũ liệu không thành công");
                }
            } else {
                throw new Exception("Chapter không tồn tại");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
