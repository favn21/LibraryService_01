package com.example.api.assertions;


import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;

import static org.junit.jupiter.api.Assertions.*;


public class BookAssertions {

    public static void verifyCreateBookResponse(CreateBookResponse response, int expectedHttpStatusCode) {
        assertNotNull(response);
        assertNotNull(response.getBookId());
        assertEquals(expectedHttpStatusCode, response.getHttpStatusCode());
    }

    public static void verifyGetBooksByAuthorResponse(GetBooksByAuthorResponse response, int expectedHttpStatusCode) {
        assertNotNull(response);
        assertNotNull(response.getBooks());
        assertEquals(expectedHttpStatusCode, response.getHttpStatusCode());
        for (GetBooksByAuthorResponse.BookDetail book : response.getBooks()) {
            assertNotNull(book.getId());
            assertNotNull(book.getBookTitle());
            assertNotNull(book.getAuthor());
            assertNotNull(book.getAuthor().getId());
            assertNotNull(book.getAuthor().getFirstName());
            assertNotNull(book.getAuthor().getSecondName());
            assertNotNull(book.getAuthor().getFamilyName());
        }
        assertNotNull(response.getBooks().get(0).getId());
        assertNotNull(response.getBooks().get(0).getBookTitle());
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