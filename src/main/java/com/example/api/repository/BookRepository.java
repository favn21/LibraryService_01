package com.example.api.repository;


import com.example.api.db.Book;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.Query;


import javax.persistence.EntityTransaction;


public class BookRepository {

    private final EntityManager entityManager;

    public BookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Book> findAll() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class)
                .getResultList();
    }


    public void insertBook(String bookTitle, long authorId) {

        Book author = entityManager.find(Book.class, authorId);
        if (author == null) {
            throw new IllegalArgumentException("Author with id " + authorId + " does not exist.");
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Query query = entityManager.createNativeQuery("INSERT INTO book (book_title, author_id) VALUES(:bookTitle, :authorId)");
            query.setParameter("bookTitle", bookTitle);
            query.setParameter("authorId", authorId);
            int rowsAffected = query.executeUpdate();

            transaction.commit();

            if (rowsAffected == 0) {
                throw new RuntimeException("Failed to insert book: no rows affected.");
            }
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to insert book.", e);
        }
    }

    public Book findBookByTitle(String bookTitle) {
        return entityManager.createQuery("SELECT b FROM Book b WHERE b.bookTitle = :bookTitle", Book.class)
                .setParameter("bookTitle", bookTitle)
                .getSingleResult();
    }

    public void deleteBook(long bookId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Query query = entityManager.createQuery("DELETE FROM Book b WHERE b.id = :bookId");
            query.setParameter("bookId", bookId);
            query.executeUpdate();

            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}