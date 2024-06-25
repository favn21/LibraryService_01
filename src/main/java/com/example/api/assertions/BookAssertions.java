package com.example.api.assertions;

import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;
import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookAssertions {

    public static void verifyCreateBookResponse(Response response, int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode());

        CreateBookResponse createBookResponse = response.as(CreateBookResponse.class);
        assertNotNull(createBookResponse);
        assertNotNull(createBookResponse.getBookId());

        assertThat(createBookResponse.getBookId(), is(greaterThan(0L)));
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
                assertThat(book.getId(), allOf(notNullValue(), greaterThan(0L)));
                assertThat(book.getBookTitle(), allOf(notNullValue(), not(emptyString())));

                assertThat(book.getAuthor(), notNullValue());
                assertThat(book.getAuthor().getId(), allOf(notNullValue(), greaterThan(0L)));
                assertThat(book.getAuthor().getFirstName(), allOf(notNullValue(), not(emptyString())));
                assertThat(book.getAuthor().getSecondName(), allOf(notNullValue(), not(emptyString())));
                assertThat(book.getAuthor().getFamilyName(), allOf(notNullValue(), not(emptyString())));
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