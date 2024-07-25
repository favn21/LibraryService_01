package com.example.api.tests;


import com.example.api.repository.BookRepository;
import com.example.api.service.TokenService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public abstract class BaseTest {

    protected static TokenService tokenService;
    protected static ObjectMapper mapper;
    protected static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected BookRepository bookRepository;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = getBaseURI();
        tokenService = new TokenService();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        entityManagerFactory = Persistence.createEntityManagerFactory("LibraryPersistenceUnit");
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

    protected static String getBaseURI() {
        return "http://localhost:8080/library";
    }
}

