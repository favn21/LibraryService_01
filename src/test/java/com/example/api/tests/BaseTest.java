package com.example.api.tests;


import com.example.api.repository.BookRepository;
import com.example.api.service.TokenService;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public abstract class BaseTest {
    private static final int PORT = 8080;
    private static final String AUTH_URL = "/api/auth";

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
        bookRepository.clearBooks();
    }

    protected static String getBaseURI() {
        return "http://localhost:" + PORT;
    }

}

