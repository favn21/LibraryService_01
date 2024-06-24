package com.example.api.tests;


import com.example.api.service.RequestBuilder;
import com.example.api.steps.BookApiRequests;
import com.example.api.steps.ErrorBookApiRequests;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.api.assertions.BookAssertions;
import io.restassured.response.Response;

import static com.example.api.service.RequestBuilder.*;

@Epic("LibraryService")
@Story("Cоздать книгу")
public class CreateBookTest extends BaseTest {

    private final BookApiRequests bookSteps = new BookApiRequests();

    @Test
    @DisplayName("Позитивный тест - Сохранение новой книги")
    @Description("Проверка, что книга успешно создается с валидными данными")
    public void testCreateBook() {
        RequestBuilder.installSpecification(requestSpec(), responseStatusCode(201));
        Response response = bookSteps.createBook("Детство", 2L);

        BookAssertions.verifyCreateBookResponse(response, 201);
    }

    @Test
    @DisplayName("Негативный тест - Создание книги без указания названия")
    @Description("Проверка, что при попытке создать книгу без названия возвращается ошибка")
    public void testCreateBookWithoutTitle() {
        Response response = ErrorBookApiRequests.createBookWithError(3L, null, 400);

        BookAssertions.verifyFailedResponse(response, 400, 1001, "Не передан обязательный параметр: bookTitle", "Не передано наименование книги");
    }

    @Test
    @DisplayName("Негативный тест - Создание книги с несуществующим автором")
    @Description("Проверка, что при попытке создать книгу с несуществующим автором возвращается ошибка")
    public void testCreateBookWithNonExistingAuthor() {
        RequestBuilder.installSpecification(requestSpec(), responseStatusCode(409));
        Response response = ErrorBookApiRequests.createBookWithError(999L, "Детство", 409);

        BookAssertions.verifyFailedResponse(response, 409, 1004, "Указанный автор не существует в таблице", null);
    }

    @Test
    @DisplayName("Негативный тест - Создание книги с ошибкой сохранения")
    @Description("Проверка, что при ошибке сохранения возвращается ошибка сервера")
    public void testCreateBookWithSavingError() {
        RequestBuilder.installSpecification(requestSpec(), responseStatusCode(500));
        Long authorId = 5L;
        String bookTitle = "Детство";
        int expectedStatusCode = 500;

        Response response = ErrorBookApiRequests.createBookWithError(authorId, bookTitle, expectedStatusCode);

        BookAssertions.verifyFailedResponse(response, expectedStatusCode, 1003, "Ошибка сохранения данных", "Ошибка сервера");
    }
}