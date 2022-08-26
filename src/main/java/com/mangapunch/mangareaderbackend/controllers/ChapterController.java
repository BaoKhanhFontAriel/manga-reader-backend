package com.mangapunch.mangareaderbackend.controllers;

import com.mangapunch.mangareaderbackend.dto.ChapterRequest;
import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.service.ChapterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @GetMapping("")
    public List<Chapter> listAllChapter() {
        return chapterService.getAllChapters();
    }

    // get Chapter detail
    @GetMapping("/{chapterid}")
    public Chapter getChapterById(@PathVariable("chapterid") long id) {
        return chapterService.findChapterById(id);
    }

    // add new Chapter
    @GetMapping("/add")
    public String showAddChapterForm(Model model) {
        model.addAttribute("ChapterForm", new ChapterRequest());
        return "add-chapter";
    }

    // Xử lý request "/Chapters" có method POST
    @PostMapping(value = "/add", consumes = { "multipart/form-data" })
    public String addChapter(@Valid @ModelAttribute("ChapterForm") ChapterRequest ChapterRequest, BindingResult bindingResult,
            Model model) {
        Chapter Chapter = modelMapper.map(ChapterRequest, Chapter.class);
        chapterService.addChapter(Chapter);
        return "redirect:/chapters";

    }

    // Xử lý request "/Chapters/{id}" có method PUT
    @RequestMapping("/edit/{id}")
    public String editChapter(@RequestParam("id") Long ChapterId, Model model) {
        Optional<Chapter> ChapterEdit = chapterService.getChapterByid(ChapterId);
        ChapterEdit.ifPresent(Chapter -> model.addAttribute("Chapter", ChapterEdit));
        return "edit-chapter";

    }

    // Xử lý request "/Chapters/{id}" có method DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteChapter(@PathVariable("id") Long ChapterId) {
        chapterService.deleteChapter(ChapterId);
        return "redirect:/chapters";
    }

    @GetMapping("/{chapterid}/pages")
    public String[] getPagesByChapterId(@PathVariable("chapterid") long id) {
        Chapter chapter = chapterService.findChapterById(id);
        String[] pages = chapter.getPageUrls().split(" ");
        return pages;
    }

    // @GetMapping("/{mangaId}/chapter")
    // List<Chapter> findByMangaId(@PathVariable("mangaid") long mangaId){
    //     return chapterService.findByMangaId(mangaId);
    // };
}
