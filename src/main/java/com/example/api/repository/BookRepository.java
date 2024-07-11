package com.example.api.repository;


import com.example.api.db.Author;
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
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();


            Author author = entityManager.find(Author.class, authorId);
            if (author == null) {
                throw new IllegalArgumentException("Author with id " + authorId + " does not exist.");
            }


            Book book = new Book();
            book.setBookTitle(bookTitle);
            book.setAuthor_id(authorId);
            entityManager.persist(book);

            transaction.commit();
        } catch (IllegalArgumentException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } catch (RuntimeException e) {
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
            query.setParameter("book_Id", bookId);
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