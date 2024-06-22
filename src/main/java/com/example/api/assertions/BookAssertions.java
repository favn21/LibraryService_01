package com.example.api.assertions;

import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;
import io.restassured.response.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookAssertions {

    public static void verifyCreateBookResponse(Response response, int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode());

        CreateBookResponse createBookResponse = response.as(CreateBookResponse.class);
        assertNotNull(createBookResponse);
        assertNotNull(createBookResponse.getBookId());
    }

    public static void verifyGetBooksByAuthorResponse(Response response, int expectedStatusCode, int expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails) {
        assertEquals(expectedStatusCode, response.getStatusCode());

        GetBooksByAuthorResponse getBooksByAuthorResponse = response.as(GetBooksByAuthorResponse.class);
        assertNotNull(getBooksByAuthorResponse);

        if (expectedStatusCode != 200) {
            assertEquals(expectedErrorCode, getBooksByAuthorResponse.getErrorCode());
            assertEquals(expectedErrorMessage, getBooksByAuthorResponse.getErrorMessage());
            assertEquals(expectedErrorDetails, getBooksByAuthorResponse.getErrorDetails());
        } else {
            List<GetBooksByAuthorResponse.BookDetail> books = getBooksByAuthorResponse.getBooks();
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
    }

    public static void verifyFailedResponse(Response response, int expectedStatusCode, int expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails) {
        assertEquals(expectedStatusCode, response.getStatusCode());

        BaseResponse baseResponse = response.as(BaseResponse.class);
        assertNotNull(baseResponse);
        assertEquals(expectedErrorCode, baseResponse.getErrorCode());
        assertEquals(expectedErrorMessage, baseResponse.getErrorMessage());
        if (expectedErrorDetails != null) {
            assertEquals(expectedErrorDetails, baseResponse.getErrorDetails());
        } else {
            assertNull(baseResponse.getErrorDetails());
        }
    }
}