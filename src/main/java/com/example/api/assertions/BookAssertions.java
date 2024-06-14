package com.example.api.assertions;


import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



public class BookAssertions {

    public static void verifyCreateBookResponse(CreateBookResponse response) {
        assertNotNull(response);
        assertNotNull(response.getBookId());
    }

    public static void verifyGetBooksByAuthorResponse(GetBooksByAuthorResponse response) {
        assertNotNull(response);
        assertNotNull(response.getBooks());
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
    public static void verifyFailedCreateBookResponse(CreateBookResponse response, int expectedStatusCode, int expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails)  {
        assertNotNull(response);
        assertNotNull(response.getErrorCode());
        assertEquals(expectedErrorCode, response.getErrorCode());
        assertEquals(expectedStatusCode, response.getHttpStatusCode());
        assertNotNull(response.getErrorMessage());
        assertEquals(expectedErrorMessage, response.getErrorMessage());
        assertNotNull(response.getErrorDetails());
        assertEquals(expectedErrorDetails, response.getErrorDetails());
    }
    public static void verifyFailedGetBooksByAuthorResponse(GetBooksByAuthorResponse response, int expectedStatusCode, int expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails)  {
        assertNotNull(response);
        assertNotNull(response.getErrorCode());
        assertEquals(expectedErrorCode, response.getErrorCode());
        assertEquals(expectedStatusCode, response.getHttpStatusCode());
        assertNotNull(response.getErrorMessage());
        assertEquals(expectedErrorMessage, response.getErrorMessage());
        assertNotNull(response.getErrorDetails());
        assertEquals(expectedErrorDetails, response.getErrorDetails());
    }
    public static void verifyStatusCode(int actualStatusCode, int expectedStatusCode) {
        assertEquals(expectedStatusCode, actualStatusCode);
    }
}