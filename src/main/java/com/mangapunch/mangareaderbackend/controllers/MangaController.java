package com.mangapunch.mangareaderbackend.controllers;

import com.mangapunch.mangareaderbackend.dto.MangaRequest;
import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.service.ChapterService;
import com.mangapunch.mangareaderbackend.service.MangaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/mangas")
@RestController
public class MangaController {

    @Autowired
    private MangaService mangaService;

    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ModelMapper modelMapper;

    // get all mangas
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("")
    public List<Manga> getAllMangas(@RequestParam int page) {
        return mangaService.getAllMangaListByUpdate(page);
    }

    // get manga detail
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/{id}")
    public String showMangaDetail(Model model, @PathVariable("id") Long mangaId) {
        model.addAttribute("manga", mangaService.getMangaById(mangaId));
        return "manga";
    }

    // add new manga
    @Secured("ROLE_ADMIN")
    @GetMapping("/add")
    public String showAddMangaForm(Model model) {
        model.addAttribute("mangaForm", new MangaRequest());
        return "add-manga";
    }

    // Xử lý request "/mangas" có method POST
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/update", consumes = { "multipart/form-data" })
    public String addManga(@Valid @ModelAttribute("mangaForm") MangaRequest mangaRequest, BindingResult bindingResult,
            Model model) {
        Manga manga = modelMapper.map(mangaRequest, Manga.class);
        mangaService.addManga(manga);
        return "redirect:/mangas";

    }

    // Xử lý request "/mangas/{id}" có method PUT
    @Secured("ROLE_ADMIN")
    @RequestMapping("/edit/{id}")
    public String editManga(@RequestParam("id") Long mangaId, Model model) {
        Optional<Manga> mangaEdit = mangaService.getMangaById(mangaId);
        mangaEdit.ifPresent(manga -> model.addAttribute("manga", mangaEdit));
        return "edit-manga";

    }

    // Xử lý request "/mangas/{id}" có method DELETE
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public String deleteManga(@PathVariable("id") Long mangaId) {
        mangaService.deleteManga(mangaId);

        return "redirect:/mangas";
    }
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/top/most-views")
    public List<Manga> findTop5MangasWithMostViews() {
        return mangaService.findTop5MangasWithMostViews();
    }
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/{mangaId}/chapters/latest")
    public Chapter getLatestChapterByMangaId(@PathVariable("mangaId") long mangaId) {
        return chapterService.getLatestChapterByMangaId(mangaId);
    }
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/{mangaId}/total-views")
    public Long getTotalViewsByMangaId(@PathVariable("mangaId") long mangaId) {
        return mangaService.getTotalViewsByMangaId(mangaId);
    };
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/top/most-favorites")
    public List<Manga> findTop5MangasByFavorite() {
        return mangaService.findTop5MangasByFavorite();
    };
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/{mangaId}/favorites")
    public int getNumberOfFavoriteByMangaId(@PathVariable("mangaId") long mangaId) {
        return mangaService.getNumberOfFavoriteByMangaId(mangaId);
    };
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/manga")
    public Manga findMangaById(@RequestParam("id") long mangaid) {
        return mangaService.findById(mangaid);
    };
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/{mangaId}/genres")
    public List<String> getGenresByMangaId(@PathVariable("mangaId") long mangaid) {
        return mangaService.getGenresByMangaId(mangaid);
    };
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/{mangaId}/update-datetime")
    public String getUpdateDateTimeByMangaId(@PathVariable("mangaId") long mangaid) {
        return mangaService.getUpdateDateTimeByMangaId(mangaid);
    }
    @Secured({"ROLE_USER", "ROLE_ADMIN"})

    @GetMapping("/{id}/chapters")
    List<Chapter> findChapterByMangaId(@PathVariable("id") long mangaId){
        return chapterService.findByMangaId(mangaId);
    };

}
