package com.example.api.assertions;


import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;

import static org.junit.jupiter.api.Assertions.*;


public class
BookAssertions {

    public static void verifyCreateBookResponse(CreateBookResponse response) {
        assertNotNull(response);
        assertNotNull(response.getBookId());
        assertEquals(201, response.getHttpStatusCode());
    }

    public static void verifyGetBooksByAuthorResponse(GetBooksByAuthorResponse response) {
        assertNotNull(response);
        assertNotNull(response.getBooks());
        assertEquals(201, response.getHttpStatusCode());
        assertFalse(response.getBooks().isEmpty());
        for (GetBooksByAuthorResponse.BookDetail book : response.getBooks()) {
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

    public static void verifyResponseWithEmptyList(GetBooksByAuthorResponse response, int expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails){
        assertNotNull(response);
        assertEquals(expectedErrorCode, response.getHttpStatusCode());
        assertEquals(expectedErrorMessage, response.getErrorMessage());
        assertTrue(response.getBooks().isEmpty());
        assertEquals(expectedErrorDetails, response.getErrorDetails());
    }
}