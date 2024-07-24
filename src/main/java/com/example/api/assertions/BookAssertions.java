package com.example.api.assertions;

import com.example.api.database.DatabaseHelper;
import com.example.api.db.Book;
import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse.BookDetail;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookAssertions {
    private final EntityManager entityManager;

    public BookAssertions(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void verifyCreateBookResponse(CreateBookResponse createBookResponse) {
        assertNotNull(createBookResponse);
        assertThat(createBookResponse.getBookId(), is(greaterThan(0L)));

        Book bookInDb = DatabaseHelper.getRecordByField(entityManager, Book.class, "id", createBookResponse.getBookId());
        assertNotNull(bookInDb, "Книга должна быть сохранена в базе данных");
        assertThat(bookInDb.getId(), is(createBookResponse.getBookId()));
    }


    public void verifyGetBooksByAuthorResponse(List<BookDetail> actualBooks, List<BookDetail> expectedBooks) {
        assertNotNull(actualBooks);
        assertEquals(expectedBooks.size(), actualBooks.size());

        for (int i = 0; i < actualBooks.size(); i++) {
            BookDetail actualBook = actualBooks.get(i);
            BookDetail expectedBook = expectedBooks.get(i);

            assertThat(actualBook.getId(), is(expectedBook.getId()));
            assertThat(actualBook.getBookTitle(), is(expectedBook.getBookTitle()));
            assertThat(actualBook.getAuthor(), allOf(
                    notNullValue(),
                    hasProperty("id", is(expectedBook.getAuthor().getId())),
                    hasProperty("firstName", is(expectedBook.getAuthor().getFirstName())),
                    hasProperty("secondName", is(expectedBook.getAuthor().getSecondName())),
                    hasProperty("familyName", is(expectedBook.getAuthor().getFamilyName())),
                    hasProperty("birthDate", is(expectedBook.getAuthor().getBirthDate().format(DateTimeFormatter.ISO_DATE)))
            ));

            Book bookInDb = DatabaseHelper.getRecordByField(entityManager, Book.class, "id", actualBook.getId());
            assertNotNull(bookInDb, "Книга должна быть в базе данных");
            assertThat(bookInDb.getBookTitle(), is(actualBook.getBookTitle()));
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
        Book book = query.getSingleResult();

        assertNotNull(book);
        assertEquals(expectedTitle, book.getBookTitle());
        assertEquals(expectedAuthorId, book.getAuthor_id());
    }

    public void verifyBookNotInDatabase(Long bookId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Book b WHERE b.id = :bookId", Long.class);
        query.setParameter("bookId", bookId);
        Long count = query.getSingleResult();

        assertEquals(0L, count);
    }

    public void verifyNoBooksInDatabaseWithAuthorId(Long authorId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Book b WHERE b.author_id = :authorId", Long.class);
        query.setParameter("authorId", authorId);
        Long count = query.getSingleResult();

        assertEquals(0L, count);
    }
}