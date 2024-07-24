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

    BookAssertions bookAssertions = new BookAssertions(entityManager);

    @Test
    @DisplayName("Позитивный тест - Получение книг по автору (JSON)")
    @Description("Проверка, что можно получить книги автора в формате JSON")
    public void testGetBooksByAuthorJSON() {
        List<GetBooksByAuthorResponse.BookDetail> expectedBooks = new ArrayList<>();
        List<GetBooksByAuthorResponse.BookDetail> response = BookApiRequests.getBooksByAuthor(2L, 200);
        bookAssertions.verifyGetBooksByAuthorResponse(response, expectedBooks);
        expectedBooks.forEach(book -> bookAssertions.verifyBookInDatabase(book.getId(), book.getBookTitle(), 2L));
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору без указания ID")
    @Description("Проверка, что при запросе без ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithoutId() {
        BaseResponse response = ErrorBookApiRequests.getBooksByAuthorWithError(0L, 409);
        bookAssertions.verifyFailedResponse(response, "1001", "Не передан обязательный параметр: autherId", "Не передан id автора");

        bookAssertions.verifyNoBooksInDatabaseWithAuthorId(0L);
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с несуществующим ID")
    @Description("Проверка, что при запросе с несуществующим ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithNonexistentId() {
        BaseResponse response = ErrorBookApiRequests.getBooksByAuthorWithError(999L, 409);
        bookAssertions.verifyFailedResponse(response, "1004", "Указанный автор не существует в таблице", null);

        bookAssertions.verifyNoBooksInDatabaseWithAuthorId(999L);
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с недопустимым значением id")
    @Description("Проверка, что при передаче недопустимого отрицательного значения id возвращается ошибка сервера")
    public void testGetBooksByAuthorWithError() {
        Long invalidAuthorId = -1L;
        int expectedStatusCode = 400;

        BaseResponse response = ErrorBookApiRequests.getBooksByAuthorWithError(invalidAuthorId, expectedStatusCode);
        bookAssertions.verifyFailedResponse(response, "1005", "Ошибка получения данных", "Недопустимое значение id");

        bookAssertions.verifyNoBooksInDatabaseWithAuthorId(invalidAuthorId);
    }
}