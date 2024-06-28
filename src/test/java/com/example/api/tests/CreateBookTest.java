package com.example.api.tests;

import com.example.api.steps.BookApiRequests;
import com.example.api.steps.ErrorBookApiRequests;
import com.example.api.assertions.BookAssertions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.api.service.RequestBuilder.*;

@Epic("LibraryService")
@Story("Создать книгу")
public class CreateBookTest extends BaseTest {

    private final BookApiRequests bookSteps = new BookApiRequests();

    @Test
    @DisplayName("Позитивный тест - Сохранение новой книги")
    @Description("Проверка, что книга успешно создается с валидными данными")
    public void testCreateBook() {
        installSpecification(requestSpec(), responseStatusCode(201));
        Response response = bookSteps.createBook("Детство", 2L);
        BookAssertions.verifyCreateBookResponse(response, 201);
    }

    @Test
    @DisplayName("Негативный тест - Создание книги без указания названия")
    @Description("Проверка, что при попытке создать книгу без названия возвращается ошибка")
    public void testCreateBookWithoutTitle() {
        installSpecification(requestSpec(), responseStatusCode(400));
        Response response = ErrorBookApiRequests.createBookWithError(3L, null, 400);
        BookAssertions.verifyFailedResponse(response, 400, 1001, "Не передан обязательный параметр: bookTitle", "Не передано наименование книги");
    }

    @Test
    @DisplayName("Негативный тест - Создание книги с несуществующим автором")
    @Description("Проверка, что при попытке создать книгу с несуществующим автором возвращается ошибка")
    public void testCreateBookWithNonExistingAuthor() {
        installSpecification(requestSpec(), responseStatusCode(409));
        Response response = ErrorBookApiRequests.createBookWithError(999L, "Детство", 409);
        BookAssertions.verifyFailedResponse(response, 409, 1004, "Указанный автор не существует в таблице", null);
    }

    @Test
    @DisplayName("Негативный тест - Создание книги с ошибкой сохранения")
    @Description("Проверка, что при ошибке сохранения возвращается ошибка сервера")
    public void testCreateBookWithSavingError() {
        Long authorId = 5L;
        String bookTitle = "Детство";
        int expectedStatusCode = 500;

        installSpecification(requestSpec(), responseStatusCode(expectedStatusCode));

        Response response = ErrorBookApiRequests.createBookWithErrorAndMock(authorId, bookTitle, expectedStatusCode);

        int expectedErrorCode = 1003;
        String expectedErrorMessage = "Ошибка сохранения данных";
        String expectedErrorDetails = "Ошибка сервера";

        BookAssertions.verifyFailedResponse(response, expectedStatusCode, expectedErrorCode, expectedErrorMessage, expectedErrorDetails);
    }
}