package com.example.api.tests;


import com.example.api.steps.BookApiRequests;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.steps.ErrorBookApiRequests;
import io.qameta.allure.Description;
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
    @DisplayName("Позитивный тест - Сохранение новой книги")
    @Description("Проверка, что книга успешно создается с валидными данными")
    public void testCreateBook() {
        CreateBookResponse response = bookSteps.createBook("Детство", 2L);

        BookAssertions.verifyCreateBookResponse(response, 201);
    }
    @Test
    @DisplayName("Негативный тест - Создание книги без указания названия")
    @Description("Проверка, что при попытке создать книгу без названия возвращается ошибка")
    public void testCreateBookWithoutTitle() {

        CreateBookResponse response = ErrorBookApiRequests.createBookWithError(3L, null, 400);

        BookAssertions.verifyFailedResponse(response,400, 1001,"не передан обязательный параметр или не пройдена валидация",null);
    }

    @Test
    @DisplayName("Негативный тест - Создание книги с несуществующим автором")
    @Description("Проверка, что при попытке создать книгу с несуществующим автором возвращается ошибка")
    public void testCreateBookWithNonExistingAuthor() {
        CreateBookResponse response = ErrorBookApiRequests.createBookWithError(999L, "Детство", 409);

        BookAssertions.verifyFailedResponse(response, 409, 1004, "Указанный автор не существует в таблице",null );
    }

    @Test
    @DisplayName("Негативный тест - Создание книги с ошибкой сохранения")
    @Description("Проверка, что при ошибке сохранения возвращается ошибка сервера")
    public void testCreateBookWithSavingError() {
        CreateBookResponse response = ErrorBookApiRequests.createBookWithError(5L, "Детство", 500);

        BookAssertions.verifyFailedResponse(response, 500, 1003, "Ошибка сохранения данных",null);
    }
}