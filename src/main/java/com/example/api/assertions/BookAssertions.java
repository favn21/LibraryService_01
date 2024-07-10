package com.example.api.assertions;

import com.example.api.database.DatabaseHelper;
import com.example.api.db.Book;
import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse.BookDetail;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookAssertions {

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
        Book bookInDb = DatabaseHelper.getRecordByField(Book.class, "id", bookId);
        assertNotNull(bookInDb, "Книга должна быть сохранена в базе данных");
        assertEquals(expectedTitle, bookInDb.getBookTitle(), "Название книги должно совпадать");
        assertEquals(expectedAuthorId, bookInDb.getAuthor_id(), "ID автора должен совпадать");
    }

    public static void verifyBookNotInDatabase(Long bookId) {
        Book bookInDb = DatabaseHelper.getRecordByField(Book.class, "id", bookId);
        assertNull(bookInDb, "Книга не должна быть в базе данных");
    }
    public static void verifyNoBooksInDatabaseWithAuthorId(Long authorId) {
        List<Book> booksInDb = DatabaseHelper.getRecordsByField(Book.class, "authorId", authorId);
        assertTrue(booksInDb.isEmpty(), "В базе данных не должно быть книг с указанным authorId");
    }

}