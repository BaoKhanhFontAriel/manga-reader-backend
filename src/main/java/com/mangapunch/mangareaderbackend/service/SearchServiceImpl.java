package com.mangapunch.mangareaderbackend.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangapunch.mangareaderbackend.dto.SearchResponse;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.MangaPageSize;

@Component
public class SearchServiceImpl implements SearchService {

        @Autowired
        private EntityManager entityManager;

        @Override
        public SearchResponse getSearchedMangas(String keywords, int page,
                        String selectedGenres, String unselectedGenres) {

                String[] selectedGenresArr = selectedGenres.split(" ");
                String[] unSelectedGenresArr = unselectedGenres.split(" ");

                FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

                QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                                .buildQueryBuilder()
                                .forEntity(Manga.class)
                                .get();

                // Search for mangas with title or author contain keywords
                Query authorAndTitlePhraseQuery = queryBuilder
                                .phrase()
                                .withSlop(5)
                                .onField("title").andField("author")
                                .sentence(keywords)
                                .createQuery();

                Query authorAndTitleKeywordQuery = queryBuilder
                                .keyword()
                                .wildcard()
                                .onField("title").andField("author")
                                .matching("*" + keywords + "*")
                                .createQuery();

                BooleanJunction<?> bool = queryBuilder.bool();

                if (!selectedGenres.isEmpty()) {
                        for (String genre : selectedGenresArr) {
                                bool.must(queryBuilder.keyword().onField("genres.value").matching(genre).createQuery());
                        }
                }

                if (!unselectedGenres.isEmpty()) {
                        for (String genre : unSelectedGenresArr) {
                                bool.must(queryBuilder.keyword().onField("genres.value").matching(genre).createQuery())
                                                .not();
                        }
                }

                if (!keywords.isEmpty()) {
                        BooleanJunction<?> subBool = queryBuilder.bool();

                        bool.must(subBool.should(authorAndTitlePhraseQuery)
                                        .should(authorAndTitleKeywordQuery)
                                        .createQuery());

                }

                Query query = bool.createQuery();

                FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
                                Manga.class);

                int repositoryPage = page * MangaPageSize.MANGA_PAGE_SIZE_6_X_4;
                jpaQuery.setFirstResult(repositoryPage); // start from the 0th
                                                         // element
                jpaQuery.setMaxResults(MangaPageSize.MANGA_PAGE_SIZE_6_X_4); // return 16 elements
                // total pages of search results
                int totalPages = (int) Math.ceil((double) jpaQuery.getResultSize() /
                                MangaPageSize.MANGA_PAGE_SIZE_6_X_4);

                List<Manga> results = jpaQuery.getResultList();

                SearchResponse searchResponse = new SearchResponse();
                searchResponse.setTotalPages(totalPages);
                searchResponse.setMangas(results);
                return searchResponse;
        }

        @Override
        public SearchResponse getQuickSearchedMangas(String keywords) {
                FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

                QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                                .buildQueryBuilder()
                                .forEntity(Manga.class)
                                .get();

                // Search for mangas with title or author contain keywords
                Query authorAndTitlePhraseQuery = queryBuilder
                                .phrase()
                                .withSlop(5)
                                .onField("title").andField("author")
                                .sentence(keywords)
                                .createQuery();

                Query authorAndTitleKeywordQuery = queryBuilder
                                .keyword()
                                .wildcard()
                                .onField("title").andField("author")
                                .matching("*" + keywords + "*")
                                .createQuery();

                BooleanJunction<?> bool = queryBuilder.bool();

                if (!keywords.isEmpty()) {
                        bool
                                        .should(authorAndTitlePhraseQuery)
                                        .should(authorAndTitleKeywordQuery)
                                        .createQuery();
                }

                Query combinedQuery = bool.createQuery();

                FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(combinedQuery,
                                Manga.class);

                int firstPage = 0;
                int repositoryPage = firstPage * MangaPageSize.MANGA_PAGE_SIZE_5_X_1;

                // start from the 0th element
                jpaQuery.setFirstResult(repositoryPage);
                // return 16 elements total pages of search results
                jpaQuery.setMaxResults(MangaPageSize.MANGA_PAGE_SIZE_5_X_1);

                int totalPages = (int) Math.ceil((double) jpaQuery.getResultSize() /
                                MangaPageSize.MANGA_PAGE_SIZE_5_X_1);
                List<Manga> results = jpaQuery.getResultList();

                SearchResponse searchResponse = new SearchResponse();
                searchResponse.setTotalPages(totalPages);
                searchResponse.setMangas(results);
                return searchResponse;
        }

}
