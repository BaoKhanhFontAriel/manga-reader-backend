package com.mangapunch.mangareaderbackend.controllers;

import com.mangapunch.mangareaderbackend.dto.ChapterRequest;
import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.security.UserPrincipal;
import com.mangapunch.mangareaderbackend.service.ChapterService;
import com.mangapunch.mangareaderbackend.service.MangaService;
import com.mangapunch.mangareaderbackend.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/chapters")
@RestController
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private MangaService mangaService;
    @Autowired
    private ModelMapper modelMapper;

    // get all Chapters
    @GetMapping("")
    public List<Chapter> listAllChapter() {
        return chapterService.getAllChapters();
    }

    // get Chapter detail
    @GetMapping("/{chapterid}")
    public Chapter getChapterById(@PathVariable("chapterid") long id) {
        return chapterService.findChapterById(id);
    }

    // Xử lý request cập nhật thông tin chương truyện
    @RolesAllowed("ROLE_USER")
    @PutMapping("/{chapterId}/edit")
    public ResponseEntity<?> editChapter(@PathVariable("chapterId") long chapterId,
            @RequestBody ChapterRequest chapterRequest) {
        Chapter chapter = chapterService.editChapterDetail(chapterId, chapterRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(chapter);
    }

    // Xử lý request user muốn chương truyện mình đã đăng tải
    @RolesAllowed("ROLE_USER")
    @DeleteMapping("/{chapterId}/delete")
    public ResponseEntity<?> deleteChapter(@PathVariable("chapterId") long chapterId) {
        chapterService.deleteChapter(chapterId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Xóa thành công");
    }

    @GetMapping("/{chapterid}/pages")
    public String[] getPagesByChapterId(@PathVariable("chapterid") long id) {
        return chapterService.getPagesByChapterId(id);
    }

    @GetMapping("/{id}/uploaded-datetime")
    public String getUploadedDateTimeByChapterId(@PathVariable("id") long id) {
        Chapter chapter = chapterService.findChapterById(id);
        LocalDateTime datetime = LocalDateTime.of(chapter.getUploadedDate(), chapter.getUploadedTime());
        String datetimeStr = datetime.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")).toString();
        return datetimeStr;
    };

    @GetMapping("/{chapterid}/manga")
    public Manga findMangaByChapterId(@PathVariable("chapterid") long chapterId) {
        return mangaService.findMangaByChapterId(chapterId);
    }
}
