package com.mangapunch.mangareaderbackend.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableTransactionManagement
public class AppConfig {
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory em) {
        return new JpaTransactionManager(em);
    }

    // @Bean
    // @Transactional
    // @Lazy
    // public FullTextEntityManager fullTextEntityManager(EntityManager em) throws InterruptedException {
    //     FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
    //     fullTextEntityManager.createIndexer().startAndWait();
    //     return fullTextEntityManager;
    // }
}
