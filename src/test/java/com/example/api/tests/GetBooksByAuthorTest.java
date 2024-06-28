package com.example.api.tests;

import com.example.api.steps.BookApiRequests;
import com.example.api.steps.ErrorBookApiRequests;
import com.example.api.assertions.BookAssertions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.api.service.RequestBuilder.*;

@Epic("LibraryService")
@Story("Получить список книг по ID автора")
public class GetBooksByAuthorTest extends BaseTest {

    private final BookApiRequests bookSteps = new BookApiRequests();

    @Test
    @DisplayName("Позитивный тест - Получение книг по автору (JSON)")
    @Description("Проверка, что можно получить книги автора в формате JSON")
    public void testGetBooksByAuthorJSON() {
        installSpecification(requestSpec(), responseStatusCode(200));
        ContentType contentType = ContentType.JSON;
        Response response = bookSteps.getBooksByAuthor(2L, contentType);
        BookAssertions.verifyGetBooksByAuthorResponse(response, 200, 0, null, null);
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору без указания ID")
    @Description("Проверка, что при запросе без ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithoutId() {
        installSpecification(requestSpec(), responseStatusCode(400));
        Response response = ErrorBookApiRequests.getBooksByAuthorWithError(0L, 400);
        BookAssertions.verifyFailedResponse(response, 400, 1001, "Не передан обязательный параметр: autherId", "Не передан id автора");
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с несуществующим ID")
    @Description("Проверка, что при запросе с несуществующим ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithNonexistentId() {
        installSpecification(requestSpec(), responseStatusCode(409));
        Response response = ErrorBookApiRequests.getBooksByAuthorWithError(999L, 409);
        BookAssertions.verifyFailedResponse(response, 409, 1004, "Указанный автор не существует в таблице", null);
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с ошибкой при получении данных")
    @Description("Проверка, что при ошибке сервера возвращается ошибка сервера")
    public void testGetBooksByAuthorWithError() {
        installSpecification(requestSpec(), responseStatusCode(500));
        Long authorId = 1L;
        int expectedStatusCode = 500;
        Response response = ErrorBookApiRequests.getBooksByAuthorWithErrorAndMock(authorId, expectedStatusCode);
        BookAssertions.verifyFailedResponse(response, expectedStatusCode, 1005, "Ошибка получения данных", "Ошибка сервера");
    }
}