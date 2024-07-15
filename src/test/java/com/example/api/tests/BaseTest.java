package com.example.api.tests;


import com.example.api.repository.BookRepository;
import com.example.api.service.TokenService;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTest {

    private static final int PORT = 8080;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected BookRepository bookRepository;
    private static TokenService tokenService;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = getBaseURI();
        entityManagerFactory = Persistence.createEntityManagerFactory("LibraryPersistenceUnit");
        tokenService = new TokenService();
    }

    @BeforeEach
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        bookRepository = new BookRepository(entityManager);
        clearBooks();
    }

    protected static String getBaseURI() {
        return "http://localhost:" + PORT;
    }

    private void clearBooks() {
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery("DELETE FROM Book").executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Failed to clear books: {}", e.getMessage());
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

}

