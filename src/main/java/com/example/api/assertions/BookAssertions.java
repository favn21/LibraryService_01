package com.example.api.assertions;

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

}