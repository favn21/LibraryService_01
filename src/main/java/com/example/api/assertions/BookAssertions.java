package com.example.api.assertions;

import com.example.api.database.DatabaseHelper;
import com.example.api.db.Book;
import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse.BookDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookAssertions {
    private final EntityManager entityManager;
    private final ObjectMapper mapper;

    public BookAssertions(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void verifyCreateBookResponse(CreateBookResponse createBookResponse) {
        assertNotNull(createBookResponse);
        assertThat(createBookResponse.getBookId(), is(greaterThan(0L)));

        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Book b WHERE b.id = :bookId", Long.class);
        query.setParameter("bookId", createBookResponse.getBookId());
        Long count = query.getSingleResult();

        assertEquals(1L, count, "Книга должна быть сохранена в базе данных");

        Book bookInDb = DatabaseHelper.getRecordByField(entityManager, Book.class, "id", createBookResponse.getBookId());
        assertNotNull(bookInDb, "Книга должна быть сохранена в базе данных");
        assertThat(bookInDb.getId(), is(createBookResponse.getBookId()));
        assertEquals("Детство", bookInDb.getBookTitle(), "Название книги должно совпадать");
        assertEquals(createBookResponse.getBookId(), bookInDb.getId(), "ID должно совпадать");
    }

    public void verifyGetBooksByAuthorResponse(List<BookDetail> actualBooks, List<BookDetail> expectedBooks) {
        assertNotNull(actualBooks,"Список книг не должен быть null");
        assertEquals(expectedBooks.size(), actualBooks.size(), "Размер списков не совпадает");

        for (int i = 0; i < actualBooks.size(); i++) {
            BookDetail actualBook = actualBooks.get(i);
            BookDetail expectedBook = expectedBooks.get(i);
            assertThat("ID книги не совпадает", actualBook.getId(), is(expectedBook.getId()));
            assertThat("Название книги не совпадает", actualBook.getBookTitle(), is(expectedBook.getBookTitle()));

            assertThat("Автор книги не совпадает", actualBook.getAuthor(), allOf(
                    notNullValue(),
                    hasProperty("id", is(expectedBook.getAuthor().getId())),
                    hasProperty("firstName", is(expectedBook.getAuthor().getFirstName())),
                    hasProperty("secondName", is(expectedBook.getAuthor().getSecondName())),
                    hasProperty("familyName", is(expectedBook.getAuthor().getFamilyName())),
                    hasProperty("birthDate", is(expectedBook.getAuthor().getBirthDate().format(DateTimeFormatter.ISO_DATE)))
            ));

            Book bookInDb = DatabaseHelper.getRecordByField(entityManager, Book.class, "id", actualBook.getId());
            assertNotNull(bookInDb,"Книга должна быть в базе данных");
            assertThat("Название книги в базе данных не совпадает", bookInDb.getBookTitle(), is(actualBook.getBookTitle()));
        }
    }

    public void verifyFailedResponse(BaseResponse baseResponse, String expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails) {
        assertNotNull(baseResponse);
        assertEquals(expectedErrorCode, baseResponse.getErrorCode());
        assertEquals(expectedErrorMessage, baseResponse.getErrorMessage());

        if (expectedErrorDetails != null) {
            assertEquals(expectedErrorDetails, baseResponse.getErrorDetails());
        } else {
            assertNull(baseResponse.getErrorDetails());
        }
    }

    public void bookListSize(int expectedSize, List<Book> books) {
        assertEquals(expectedSize, books.size(), () -> "Expected size: " + expectedSize + ", actual size: " + books.size());
    }

    public void bookNotNull(String message, Book book) {
        assertNotNull(book, message);
    }

    public void verifyBookInDatabase(Long bookId, String expectedTitle, Long expectedAuthorId) {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b WHERE b.id = :bookId", Book.class);
        query.setParameter("bookId", bookId);
        Book book = null;
        try {
            book = query.getSingleResult();
        } catch (NoResultException e) {
            fail("Книга с ID " + bookId + " не найдена в базе данных");
        }

        assertNotNull(book, "Книга должна быть сохранена в базе данных");
        assertEquals(expectedTitle, book.getBookTitle(), "Название книги должно совпадать с ожидаемым");
        assertEquals(expectedAuthorId, book.getAuthor_id(), "ID автора должен совпадать с ожидаемым");
    }

    public void verifyBookNotInDatabase(String bookTitle) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Book b WHERE b.bookTitle = :bookTitle ", Long.class);
        query.setParameter("bookTitle", bookTitle);
        Long count = query.getSingleResult();

        assertEquals(0L, count);
    }

    public void verifyNoBooksInDatabaseWithAuthorId(Long author_Id) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Book b WHERE b.author_id = :author_Id", Long.class);
        query.setParameter("author_Id", author_Id);
        Long count = query.getSingleResult();

        assertEquals(0L, count);
    }
}