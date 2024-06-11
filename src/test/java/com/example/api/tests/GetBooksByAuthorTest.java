package com.example.api.tests;

import com.example.api.db.Book;
import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.steps.BookApiRequests;
import com.example.api.steps.ErrorBookApiRequests;
import com.example.api.assertions.BookAssertions;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
@Epic("LibraryService")
@Story("Получить список книг по ID автора")
public class GetBooksByAuthorTest extends BaseTest {

    private final BookApiRequests bookSteps = new BookApiRequests();

    @Test
    @DisplayName("Получение книг по автору (JSON)")
    public void testGetBooksByAuthorJSON() {
        Long authorId = 2L;
        ContentType contentType = ContentType.JSON;

        List<Book> books = bookSteps.getBooksByAuthor(authorId, contentType);

        BookAssertions.verifyGetBooksByAuthorResponse(books);
    }


    @Test
    @DisplayName("Получение книг по автору (XML)")
    public void testGetBooksByAuthorXML() {
        Long authorId = 2L;
        ContentType contentType = ContentType.XML;

        List<Book> books = bookSteps.getBooksByAuthor(authorId, contentType);

        BookAssertions.verifyGetBooksByAuthorResponse(books);
    }
    @Test
    @DisplayName("Получение книг по автору без указания ID")
    public void testGetBooksByAuthorWithoutId() {
        GetBooksByAuthorResponse response = ErrorBookApiRequests.getBooksByAuthorWithoutId();

        BookAssertions.verifyFailedGetBooksByAuthorResponse(response, 400);
    }

    @Test
    @DisplayName("Получение книг по автору с несуществующим ID")
    public void testGetBooksByAuthorWithNonexistentId() {
        Long authorId = 999L;

        GetBooksByAuthorResponse response = ErrorBookApiRequests.getBooksByAuthorWithNonexistentId(authorId);

        BookAssertions.verifyFailedGetBooksByAuthorResponse(response, 400);
    }

    @Test
    @DisplayName("Получение книг по автору с ошибкой при получении данных")
    public void testGetBooksByAuthorWithError() {
        Long authorId = 2L;

        GetBooksByAuthorResponse response = ErrorBookApiRequests.getBooksByAuthorWithErrorInDataRetrieval(authorId);

        BookAssertions.verifyFailedGetBooksByAuthorResponse(response, 500);
    }
}