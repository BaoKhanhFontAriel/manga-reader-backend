package com.mangapunch.mangareaderbackend.test;

import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.service.ChapterService;
import com.mangapunch.mangareaderbackend.service.MangaService;
import com.mangapunch.mangareaderbackend.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
public class MangaQueryTest {
    @Autowired
    private MangaService mangaService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private UserService userService;

    // test if query the latest chapter of desired manga works
    @Test
    public void getLatestChapterByMangaIdIsNotNull() {
        Chapter latestChapter = chapterService.getLatestChapterByMangaId(1);
        assertThat(latestChapter.getTitle()).isEqualTo("Chapter 4");
    }

    @Test
    public void getTop5MostViewMangasIsNotNull() {
        List<Manga> mangas = mangaService.findTop5MangasWithMostViews();
        assertThat(mangas).isNotEmpty();
        assertThat(mangas.size()).isEqualTo(5);
    }

    @Test
    public void findMangaById() {
        Manga manga = mangaService.findById(1);
        assertThat(manga).isNotNull();
        assertThat(manga.getTitle()).isEqualTo("Duck, blue");
    }

    @Test
    public void getGenreValueByMangaId() {
        List<String> genreValues = new ArrayList<>();
        List<String> genres = mangaService.getGenresByMangaId(1);

        genres.stream().forEach(g -> genreValues.add(g));

        assertThat(genreValues.get(0)).isEqualTo("Historical");
    }

    @Test
    public void sortMangaByUpdate() {
        List<Manga> mangas = mangaService.getAllMangaListByUpdate(0);
        Optional<Manga> result = mangas.stream().findFirst();

        assertTrue(result.isPresent());
        assertThat(result.get().getId()).isEqualTo(23);
    }

    @Test
    public void findChapterById(){
        Chapter chapter = chapterService.findChapterById(1);
        assertTrue(chapter.isNotNull());
    }

    @Test
    public void findUserByUsernameOrEmail(){
        User user = userService.findByUsernameOrEmail("khanhadmin","khanhadmin");
        assertTrue(user != null);
        assertThat(user.getUsername()).isEqualTo("khanhadmin");
    }
}
