package com.mangapunch.mangareaderbackend.controllers;

import com.mangapunch.mangareaderbackend.dto.ChapterRequest;
import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.service.ChapterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/chapters")
@RestController
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ModelMapper modelMapper;

    // get all Chapters
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("")
    public List<Chapter> listAllChapter() {
        return chapterService.getAllChapters();
    }

    // get Chapter detail
    @Secured({"ROLE_USER", "ROLE_ADMIN"})    @GetMapping("/{chapterid}")
    public Chapter getChapterById(@PathVariable("chapterid") long id) {
        return chapterService.findChapterById(id);
    }

    // add new Chapter
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/add")
    public String showAddChapterForm(Model model) {
        model.addAttribute("ChapterForm", new ChapterRequest());
        return "add-chapter";
    }

    // Xử lý request "/Chapters" có method POST
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @PostMapping(value = "/add", consumes = { "multipart/form-data" })
    public String addChapter(@Valid @ModelAttribute("ChapterForm") ChapterRequest ChapterRequest, BindingResult bindingResult,
            Model model) {
        Chapter Chapter = modelMapper.map(ChapterRequest, Chapter.class);
        chapterService.addChapter(Chapter);
        return "redirect:/chapters";

    }

    // Xử lý request "/Chapters/{id}" có method PUT
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @RequestMapping("/edit/{id}")
    public String editChapter(@RequestParam("id") Long ChapterId, Model model) {
        Optional<Chapter> ChapterEdit = chapterService.getChapterByid(ChapterId);
        ChapterEdit.ifPresent(Chapter -> model.addAttribute("Chapter", ChapterEdit));
        return "edit-chapter";

    }

    // Xử lý request "/Chapters/{id}" có method DELETE
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @DeleteMapping("/delete/{id}")
    public String deleteChapter(@PathVariable("id") Long ChapterId) {
        chapterService.deleteChapter(ChapterId);
        return "redirect:/chapters";
    }
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/{chapterid}/pages")
    public String[] getPagesByChapterId(@PathVariable("chapterid") long id) {
        Chapter chapter = chapterService.findChapterById(id);
        String[] pages = chapter.getPageUrls().split(" ");
        return pages;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/{id}/uploaded-datetime")
    public String getUploadedDateTimeByChapterId(@PathVariable("id") long id){
        Chapter chapter = chapterService.findChapterById(id);
        LocalDateTime datetime = LocalDateTime.of(chapter.getUploadedDate(), chapter.getUploadedTime());
        String datetimeStr = datetime.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")).toString();
        return datetimeStr;
    };

    // @GetMapping("/{mangaId}/chapter")
    // List<Chapter> findByMangaId(@PathVariable("mangaid") long mangaId){
    //     return chapterService.findByMangaId(mangaId);
    // };
}
