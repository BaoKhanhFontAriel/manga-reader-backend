package com.mangapunch.mangareaderbackend.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class IndexingService {

    // @Autowired
    // private final EntityManager em;

    // @Transactional
    // public void initiateIndexing() throws InterruptedException {
    //     log.info("Initiating indexing...");
    //     FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
    //     fullTextEntityManager.createIndexer().startAndWait();
    //     log.info("All entities indexed");
    // }
}
