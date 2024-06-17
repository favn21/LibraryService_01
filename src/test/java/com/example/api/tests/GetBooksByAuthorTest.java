package com.example.api.tests;


import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.steps.BookApiRequests;
import com.example.api.steps.ErrorBookApiRequests;
import com.example.api.assertions.BookAssertions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Epic("LibraryService")
@Story("Получить список книг по ID автора")
public class GetBooksByAuthorTest extends BaseTest {

    private final BookApiRequests bookSteps = new BookApiRequests();

    @Test
    @DisplayName("Позитивный тест - Получение книг по автору (JSON)")
    @Description("Проверка, что можно получить книги автора в формате JSON")
    public void testGetBooksByAuthorJSON() {
        ContentType contentType = ContentType.JSON;

        GetBooksByAuthorResponse response = bookSteps.getBooksByAuthor(2L, contentType);

        BookAssertions.verifyGetBooksByAuthorResponse(response, 201);
    }


    @Test
    @DisplayName("Позитивный тест - Получение книг по автору (XML)")
    @Description("Проверка, что можно получить книги автора в формате XML")
    public void testGetBooksByAuthorXML() {
        ContentType contentType = ContentType.XML;

        GetBooksByAuthorResponse response = bookSteps.getBooksByAuthor(2L, contentType);
        BookAssertions.verifyGetBooksByAuthorResponse(response, 201);
    }
    @Test
    @DisplayName("Негативный тест - Получение книг по автору без указания ID")
    @Description("Проверка, что при запросе без ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithoutId() {
        GetBooksByAuthorResponse response = ErrorBookApiRequests.getBooksByAuthorWithError(0L, 400);

        BookAssertions.verifyFailedResponse(response, 400, 1001, "не передан обязательный параметр или не пройдена валидация", null);
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с несуществующим ID")
    @Description("Проверка, что при запросе с несуществующим ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithNonexistentId() {
        GetBooksByAuthorResponse response = ErrorBookApiRequests.getBooksByAuthorWithError(999L, 400);

        BookAssertions.verifyFailedResponse(response, 400, 1004, "Указанный автор не существует в таблице", null);
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с ошибкой при получении данных")
    @Description("Проверка, что при ошибке получения данных возвращается ошибка сервера")
    public void testGetBooksByAuthorWithError() {
        GetBooksByAuthorResponse response = ErrorBookApiRequests.getBooksByAuthorWithError(2L, 500);

        BookAssertions.verifyFailedResponse(response, 500, 1005, "Ошибка получения данных",null);
    }
}