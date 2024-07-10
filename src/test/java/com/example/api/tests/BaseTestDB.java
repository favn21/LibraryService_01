package com.example.api.tests;

import com.example.api.repository.BookRepository;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BaseTestDB {

    private EntityManagerFactory entityManagerFactory;
    private Session session;
    private BookRepository bookRepository;

    @BeforeAll
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("LibraryPersistenceUnit");
        session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        bookRepository = new BookRepository();
    }

    @AfterAll
    public void tearDown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @BeforeEach
    public void clearTable() {
        bookRepository.deleteAll();
    }
}
