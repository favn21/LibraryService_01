package com.example.api.tests;

import com.example.api.service.RequestBuilder;
import com.example.api.steps.BookApiRequests;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.steps.ErrorBookApiRequests;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.api.assertions.BookAssertions;

@Epic("LibraryService")
@Story("Cоздать книгу")
public class CreateBookTest extends BaseTest {

    private final BookApiRequests bookSteps = new BookApiRequests();

    @Test
    @DisplayName("Сохранение новой книги")
    public void testCreateBook() {
        String title = "Детство";
        Long authorId = 2L;

        CreateBookResponse response = bookSteps.createBook(title, authorId);

        BookAssertions.verifyCreateBookResponse(response);
    }
    @Test
    @DisplayName("Создание книги без указания названия")
    public void testCreateBookWithoutTitle() {
        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecError(400));
        CreateBookResponse response = ErrorBookApiRequests.createBookWithoutTitle(3L);
        BookAssertions.verifyFailedCreateBookResponse(response, 400);
    }

    @Test
    @DisplayName("Создание книги с несуществующим автором")
    public void testCreateBookWithNonExistingAuthor() {
        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecError(409));
        CreateBookResponse response = ErrorBookApiRequests.createBookWithNonExistingAuthor(4L);
        BookAssertions.verifyFailedCreateBookResponse(response, 409);
    }

    @Test
    @DisplayName("Создание книги с ошибкой сохранения")
    public void testCreateBookWithSavingError() {
        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecError(500));
        CreateBookResponse response = ErrorBookApiRequests.createBookWithSavingError(5L);
        BookAssertions.verifyFailedCreateBookResponse(response, 500);
    }
}