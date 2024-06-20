package com.example.api.tests;


import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.service.RequestBuilder;
import com.example.api.steps.BookApiRequests;
import com.example.api.steps.ErrorBookApiRequests;
import com.example.api.assertions.BookAssertions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.api.service.RequestBuilder.requestSpec;
import static com.example.api.service.RequestBuilder.responseSpecOK201;


@Epic("LibraryService")
@Story("Получить список книг по ID автора")
public class GetBooksByAuthorTest extends BaseTest {

    private final BookApiRequests bookSteps = new BookApiRequests();

    @Test
    @DisplayName("Позитивный тест - Получение книг по автору (JSON)")
    @Description("Проверка, что можно получить книги автора в формате JSON")
    public void testGetBooksByAuthorJSON() {
        RequestBuilder.installSpecification(requestSpec(), responseSpecOK201());
        ContentType contentType = ContentType.JSON;

        GetBooksByAuthorResponse response = bookSteps.getBooksByAuthor(2L, contentType);

        BookAssertions.verifyGetBooksByAuthorResponse(response, 201, 0, null, null);
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору без указания ID")
    @Description("Проверка, что при запросе без ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithoutId() {
        GetBooksByAuthorResponse response = ErrorBookApiRequests.getBooksByAuthorWithError(0L, 400);

        BookAssertions.verifyFailedResponse(response, 400, 1001, "Не передан обязательный параметр: autherId", "Не передан id автора");
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с несуществующим ID")
    @Description("Проверка, что при запросе с несуществующим ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithNonexistentId() {
        GetBooksByAuthorResponse response = ErrorBookApiRequests.getBooksByAuthorWithError(999L, 400);

        BookAssertions.verifyFailedResponse(response, 400, 1004, "Указанный автор не существует в таблице", "Id автора не существует");
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с ошибкой при получении данных")
    @Description("Проверка, что даже при ошибке сервера возвращается список книг автора")
    public void testGetBooksByAuthorWithError() {
        GetBooksByAuthorResponse response = ErrorBookApiRequests.getBooksByAuthorWithError(1L, 500);

        BookAssertions.verifyGetBooksByAuthorResponse(response, 500, 1005, "Ошибка получения данных", "Ошибка сервера");
    }

}