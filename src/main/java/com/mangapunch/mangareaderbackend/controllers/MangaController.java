package com.mangapunch.mangareaderbackend.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.repositories.MangaRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/mangas")
@RestController
public class MangaController {

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    public MangaController(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    // get all mangas
    @GetMapping("/")
    public ResponseEntity<List<Manga>> getListManga() {
        List<Manga> mangaList = new ArrayList<Manga>();

        // Trả về response với STATUS CODE = 200 (OK)
        // Body sẽ chứa thông tin list user
        return ResponseEntity.status(HttpStatus.OK).body(mangaList);
    }

    // Xử lý request "/mangas" có method POST
    @PostMapping("/")
    public ResponseEntity<Manga> createManga() {
        Manga manga = new Manga();
        return ResponseEntity.status(HttpStatus.OK).body(manga);

    }

    // Xử lý request "/mangas/{id}" có method PUT
    @PutMapping("/{id}")
    public ResponseEntity<Manga> updateManga() {
        Manga manga = new Manga();

        return ResponseEntity.status(HttpStatus.OK).body(manga);

    }

    // Xử lý request "/mangas/{id}" có method DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Manga> deleteManga() {
        Manga manga = new Manga();

        return ResponseEntity.status(HttpStatus.OK).body(manga);

    }
}
