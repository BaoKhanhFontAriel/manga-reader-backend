package com.mangapunch.mangareaderbackend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.mangapunch.mangareaderbackend.dto.MangaRequest;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.repositories.MangaRepository;
import com.mangapunch.mangareaderbackend.service.MangaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/mangas")
@RestController
public class MangaController {

    @Autowired
    private MangaService mangaService;
    @Autowired
    private ModelMapper modelMapper;

    // get all mangas
    @GetMapping("/")
    public String listAllManga(Model model) {
        model.addAttribute("mangas", mangaService.getAllMangas());

        return "mangas";
    }

    // get manga detail
    @GetMapping("/{id}")
    public String showMangaDetail(Model model, @PathVariable("id") Long mangaId) {
        model.addAttribute("manga", mangaService.getMangaByid(mangaId));
        return "manga";
    }

    // add new manga
    @GetMapping("/add")
    public String showAddMangaForm(Model model) {
        model.addAttribute("mangaForm", new MangaRequest());
        return "add-manga";
    }

    // Xử lý request "/mangas" có method POST
    @PostMapping(value = "/add", consumes = { "multipart/form-data" })
    public String addManga(@Valid @ModelAttribute("mangaForm") MangaRequest mangaRequest, BindingResult bindingResult,
            Model model) {
        Manga manga = modelMapper.map(mangaRequest, Manga.class);
        mangaService.addManga(manga);
        return "redirect:/mangas";

    }

    // Xử lý request "/mangas/{id}" có method PUT
    @RequestMapping("/edit/{id}")
    public String editManga(@RequestParam("id") Long mangaId, Model model) {
        Optional<Manga> mangaEdit = mangaService.getMangaByid(mangaId);
        mangaEdit.ifPresent(manga -> model.addAttribute("manga", mangaEdit));
        return "edit-manga";

    }

    // Xử lý request "/mangas/{id}" có method DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteManga(@PathVariable("id") Long mangaId) {
        mangaService.deleteManga(mangaId);

        return "redirect:/mangas";

    }
}
