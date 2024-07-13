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
    private static EntityManager entityManager;

    public BookAssertions(EntityManager entityManager) {
        BookAssertions.entityManager = entityManager;
    }

    public static void verifyCreateBookResponse( CreateBookResponse createBookResponse) {
        assertNotNull(createBookResponse);
        assertThat(createBookResponse.getBookId(), is(greaterThan(0L)));

        Book bookInDb = DatabaseHelper.getRecordByField(Book.class, "id", createBookResponse.getBookId());
        assertNotNull(bookInDb, "Книга должна быть сохранена в базе данных");
        assertThat(bookInDb.getBookTitle(), is(createBookResponse.getBookId()));
    }

    public static void verifyGetBooksByAuthorResponse(List<BookDetail> actualBooks, List<BookDetail> expectedBooks) {
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
            Book bookInDb = DatabaseHelper.getRecordByField(Book.class, "id", actualBook.getId());
            assertNotNull(bookInDb, "Книга должна быть в базе данных");
            assertThat(bookInDb.getBookTitle(), is(actualBook.getBookTitle()));
        }
    }

    public static void verifyFailedResponse( BaseResponse baseResponse, String expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails) {
        assertNotNull(baseResponse);
        assertEquals(expectedErrorCode, baseResponse.getErrorCode());
        assertEquals(expectedErrorMessage, baseResponse.getErrorMessage());

        if (expectedErrorDetails != null) {
            assertEquals(expectedErrorDetails, baseResponse.getErrorDetails());
        } else {
            assertNull(baseResponse.getErrorDetails());
        }
    }
    public static void bookListSize(int expectedSize, List<Book> books) {
        assertEquals(expectedSize, books.size(), () -> "Expected size: " + expectedSize + ", actual size: " + books.size());
    }

    public static void bookNotNull(String message, Book book) {
        assertNotNull(book, message);
    }

    public static void verifyBookInDatabase(Long bookId, String expectedTitle, Long expectedAuthorId) {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b WHERE b.id = :bookId", Book.class);
        query.setParameter("bookId", bookId);
        Book book = query.getSingleResult();

        if (book == null) {
            throw new AssertionError("Book with ID " + bookId + " does not exist in the database.");
        }
        if (!book.getBookTitle().equals(expectedTitle)) {
            throw new AssertionError("Expected title to be " + expectedTitle + " but was " + book.getBookTitle());
        }
        if (!book.getAuthor_id().equals(expectedAuthorId)) {
            throw new AssertionError("Expected author ID to be " + expectedAuthorId + " but was " + book.getAuthor_id());
        }
    }

    public static void verifyBookNotInDatabase(Long bookId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Book b WHERE b.id = :bookId", Long.class);
        query.setParameter("bookId", bookId);
        Long count = query.getSingleResult();

        if (count > 0) {
            throw new AssertionError("Book with ID " + bookId + " exists in the database, but it should not.");
        }
    }


    public static void verifyNoBooksInDatabaseWithAuthorId(Long authorId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Book b WHERE b.authorId = :authorId", Long.class);
        query.setParameter("authorId", authorId);
        Long count = query.getSingleResult();

        if (count > 0) {
            throw new AssertionError("Books with author ID " + authorId + " exist in the database, but they should not.");
        }
    }

}