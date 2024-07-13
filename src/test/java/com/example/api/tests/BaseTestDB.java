package com.example.api.tests;


import com.example.api.repository.BookRepository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class BaseTestDB {

    protected static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected BookRepository bookRepository;

    @BeforeAll
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("LibraryPersistenceUnit");
    }

    @AfterAll
    public static void tearDownAfterClass() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @BeforeEach
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        bookRepository = new BookRepository(entityManager);
        bookRepository.clearBooks();

    }

    @AfterEach
    public void tearDown() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
