package com.example.api.assertions;


import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class
BookAssertions {

    public static void verifyCreateBookResponse(CreateBookResponse response,int expectedStatusCode) {
        assertNotNull(response);
        assertNotNull(response.getBookId());
        assertEquals(expectedStatusCode, response.getHttpStatusCode());
    }

    public static void verifyGetBooksByAuthorResponse(GetBooksByAuthorResponse response, int expectedStatusCode, int expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails) {
        assertNotNull(response);
        assertEquals(expectedStatusCode, response.getHttpStatusCode());
        if (expectedStatusCode != 201) {
            assertEquals(expectedErrorCode, response.getErrorCode());
            assertEquals(expectedErrorMessage, response.getErrorMessage());
            assertEquals(expectedErrorDetails, response.getErrorDetails());
        }

        List<GetBooksByAuthorResponse.BookDetail> books = response.getBooks();
        assertNotNull(books);

        for (GetBooksByAuthorResponse.BookDetail book : books) {
            assertNotNull(book.getId());
            assertTrue(book.getId() > 0);

            assertNotNull(book.getBookTitle());
            assertFalse(book.getBookTitle().isEmpty());

            assertNotNull(book.getAuthor());
            assertNotNull(book.getAuthor().getId());
            assertTrue(book.getAuthor().getId() > 0);

            assertNotNull(book.getAuthor().getFirstName());
            assertFalse(book.getAuthor().getFirstName().isEmpty());

            assertNotNull(book.getAuthor().getSecondName());
            assertFalse(book.getAuthor().getSecondName().isEmpty());

            assertNotNull(book.getAuthor().getFamilyName());
            assertFalse(book.getAuthor().getFamilyName().isEmpty());
        }
    }

    public static void verifyFailedResponse(BaseResponse response, int expectedStatusCode, int expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails) {
        assertNotNull(response);
        assertEquals(expectedStatusCode, response.getHttpStatusCode());
        assertEquals(expectedErrorCode, response.getErrorCode());
        assertEquals(expectedErrorMessage, response.getErrorMessage());
        if (expectedErrorDetails != null) {
            assertEquals(expectedErrorDetails, response.getErrorDetails());
        } else {
            assertNull(response.getErrorDetails());
        }
    }

}