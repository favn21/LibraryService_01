package com.example.api.repository;

import com.example.api.config.LibraryDatabaseConfiguration;
import com.example.api.config.TransactionHelper;
import com.example.api.db.Book;
import org.hibernate.Session;


import java.util.List;


public class BookRepository {
    private final Session session;

    public BookRepository() {
        this.session = LibraryDatabaseConfiguration.getSession();
    }

    public List<Book> findAll() {
        final String hql = """
           SELECT b FROM Book b
           """;

        return session.createQuery(hql, Book.class)
                .getResultList();
    }

    public void deleteAll() {
        final String sql = """
           DELETE FROM book
           """;

        TransactionHelper.executeUpdate(session, sql);
    }

    public void insertBook(String bookTitle, long authorId) {
        final String sql = """
           INSERT INTO book
           (book_title, author_id)
           VALUES(:bookTitle, :authorId)
           """;

        TransactionHelper.executeUpdateWithParams(session, sql, "bookTitle", bookTitle, "authorId", authorId);
    }
    public Book findBookByTitle(String bookTitle) {
        final String hql = """
           SELECT b FROM Book b WHERE b.bookTitle = :bookTitle
           """;
        return session.createQuery(hql, Book.class)
                .setParameter("bookTitle", bookTitle)
                .uniqueResult();
    }
    public void deleteBook(long bookId) {
        final String sql = """
           DELETE FROM book WHERE id = :bookId
           """;
        TransactionHelper.executeDeleteWithParams(session, sql, "bookId", bookId);
    }
}
