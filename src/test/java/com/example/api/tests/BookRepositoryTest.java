package com.example.api.tests;

import com.example.api.assertions.BookAssertions;
import com.example.api.db.Book;
import com.example.api.repository.BookRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookRepositoryTest extends BaseTestDB{
    private EntityManagerFactory entityManagerFactory;
    private Session session;
    private BookRepository bookRepository;
    @Test
    @DisplayName("Тест работы с таблицей Book")
    public void testBookRepository() {

        bookRepository.insertBook("Книга 1", 1L);
        bookRepository.insertBook("Книга 2", 2L);

        List<Book> allBooks = bookRepository.findAll();
        BookAssertions.bookListSize(2, allBooks);

        Book bookByTitle = bookRepository.findBookByTitle("Книга 1");
        BookAssertions.bookNotNull("Запись с названием 'Книга 1' не найдена", bookByTitle);

        bookRepository.deleteBook(bookByTitle.getId());

        Book remainingBook = bookRepository.findBookByTitle("Книга 1");
        BookAssertions.bookNotNull("Оставшаяся запись 'Книга 1' не найдена", remainingBook);
        BookAssertions.bookListSize(1, List.of(remainingBook));
    }
}
