package com.example.api.tests;

import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.steps.BookApiRequests;
import com.example.api.steps.ErrorBookApiRequests;
import com.example.api.assertions.BookAssertions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.List;

import static com.example.api.service.RequestBuilder.*;

@Epic("LibraryService")
@Story("Получить список книг по ID автора")
public class GetBooksByAuthorTest extends BaseTest {

@Test
@DisplayName("Позитивный тест - Получение книг по автору (JSON)")
@Description("Проверка, что можно получить книги автора в формате JSON")
public void testGetBooksByAuthorJSON() {
    installSpecification(requestSpec(), responseStatusCode(200));
    List<GetBooksByAuthorResponse.BookDetail> expectedBooks = new ArrayList<>();
    List<GetBooksByAuthorResponse.BookDetail> response = BookApiRequests.getBooksByAuthor(2L, 200);
    BookAssertions.verifyGetBooksByAuthorResponse(response, expectedBooks);
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору без указания ID")
    @Description("Проверка, что при запросе без ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithoutId() {
        installSpecification(requestSpec(), responseStatusCode(409));
        BaseResponse  response = ErrorBookApiRequests.getBooksByAuthorWithError(0L, 409);
        BookAssertions.verifyFailedResponse(response, "1001", "Не передан обязательный параметр: autherId", "Не передан id автора");
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с несуществующим ID")
    @Description("Проверка, что при запросе с несуществующим ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithNonexistentId() {
        installSpecification(requestSpec(), responseStatusCode(409));
        BaseResponse  response = ErrorBookApiRequests.getBooksByAuthorWithError(999L, 409);
        BookAssertions.verifyFailedResponse(response, "1004", "Указанный автор не существует в таблице", null);
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с недопустимым значением id")
    @Description("Проверка, что при передаче недопустимого отрицательного значения id возвращается ошибка сервера")
    public void testGetBooksByAuthorWithError() {
        Long invalidAuthorId = -1L;
        int expectedStatusCode = 400;

        installSpecification(requestSpec(), responseStatusCode(expectedStatusCode));
        BaseResponse  response = ErrorBookApiRequests.getBooksByAuthorWithError(invalidAuthorId, expectedStatusCode);


        BookAssertions.verifyFailedResponse(response, "1005", "Ошибка получения данных", "Недопустимое значение id");
    }
}