package com.example.api.assertions;

import com.example.api.db.Book;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

public class BookAssertions {

    public static void verifyCreateBookResponse(CreateBookResponse response) {
        assertNotNull(response);
        assertNotNull(response.getBookId());
    }

    public static void verifyGetBooksByAuthorResponse(List<Book> books) {
        assertNotNull(books);
        assertNotNull(books.get(0).getId());
        assertNotNull(books.get(0).getBookTitle());
    }
    public static void verifyFailedCreateBookResponse(CreateBookResponse response, int expectedErrorCode) {
        assertNotNull(response);
        assertNotNull(response.getErrorCode());
        assertEquals(expectedErrorCode, response.getErrorCode());
        assertNotNull(response.getErrorMessage());
    }
    public static void verifyFailedGetBooksByAuthorResponse(GetBooksByAuthorResponse response, int expectedErrorCode) {
        assertNotNull(response);
        assertNotNull(response.getErrorCode());
        assertEquals(expectedErrorCode, response.getErrorCode());
        assertNotNull(response.getErrorMessage());
    }
}