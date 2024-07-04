package com.example.api.assertions;

import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BookAssertions {

    public static void verifyCreateBookResponse( CreateBookResponse createBookResponse) {
        assertNotNull(createBookResponse);
        assertThat(createBookResponse.getBookId(), is(greaterThan(0L)));
    }

    public static void verifyGetBooksByAuthorResponse(Response response, GetBooksByAuthorResponse getBooksByAuthorResponse, int expectedStatusCode, List<GetBooksByAuthorResponse.BookDetail> expectedBooks) {
        assertNotNull(getBooksByAuthorResponse);
        assertEquals(expectedStatusCode, response.getStatusCode());
        List<GetBooksByAuthorResponse.BookDetail> books = getBooksByAuthorResponse.getBooks();
        assertNotNull(books);
        assertEquals(expectedBooks.size(), books.size());

        for (int i = 0; i < books.size(); i++) {
            GetBooksByAuthorResponse.BookDetail book = books.get(i);
            GetBooksByAuthorResponse.BookDetail expectedBook = expectedBooks.get(i);

            assertThat(book.getId(), is(expectedBook.getId()));
            assertThat(book.getBookTitle(), is(expectedBook.getBookTitle()));
            assertThat(book.getAuthor(), allOf(
                    notNullValue(),
                    hasProperty("id", is(expectedBook.getAuthor().getId())),
                    hasProperty("firstName", is(expectedBook.getAuthor().getFirstName())),
                    hasProperty("secondName", is(expectedBook.getAuthor().getSecondName())),
                    hasProperty("familyName", is(expectedBook.getAuthor().getFamilyName()))
            ));
        }
    }

    public static void verifyFailedResponse(Response response, BaseResponse baseResponse, int expectedStatusCode, String expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails) {
        assertNotNull(baseResponse);
        assertEquals(expectedStatusCode, response.getStatusCode());
        assertEquals(expectedErrorCode, baseResponse.getErrorCode());
        assertEquals(expectedErrorMessage, baseResponse.getErrorMessage());

        if (expectedErrorDetails != null) {
            assertEquals(expectedErrorDetails, baseResponse.getErrorDetails());
        } else {
            assertNull(baseResponse.getErrorDetails());
        }
    }
}