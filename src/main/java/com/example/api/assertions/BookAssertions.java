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

    public static void verifyCreateBookResponse(CreateBookResponse createBookResponse, int expectedStatusCode) {
        assertNotNull(createBookResponse);
        assertNotNull(createBookResponse.getBookId());
        assertThat(createBookResponse.getBookId(), is(greaterThan(0L)));
    }

    public static void verifyGetBooksByAuthorResponse(GetBooksByAuthorResponse getBooksByAuthorResponse, int expectedStatusCode, String expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails) {
        assertNotNull(getBooksByAuthorResponse);

        if (expectedStatusCode != 200) {
            assertEquals(expectedErrorCode, getBooksByAuthorResponse.getErrorCode());
            assertEquals(expectedErrorMessage, getBooksByAuthorResponse.getErrorMessage());
            assertEquals(expectedErrorDetails, getBooksByAuthorResponse.getErrorDetails());
        } else {
            List<GetBooksByAuthorResponse.BookDetail> books = getBooksByAuthorResponse.getBooks();
            assertNotNull(books);
            assertEquals(1, books.size());

            GetBooksByAuthorResponse.BookDetail book = books.get(0);

            assertThat(book.getId(), greaterThan(0L));
            assertThat(book.getBookTitle(), equalTo("Детство"));

            GetBooksByAuthorResponse.AuthorDetail author = book.getAuthor();
            assertNotNull(author);
            assertThat(author.getId(), equalTo(2L));
            assertThat(author.getFirstName(), equalTo("Nikolay"));
            assertThat(author.getSecondName(), equalTo("Vasilyevich"));
            assertThat(author.getFamilyName(), equalTo("Gogol"));
        }
    }

    public static void verifyFailedResponse(BaseResponse baseResponse, int expectedStatusCode, String expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails) {
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