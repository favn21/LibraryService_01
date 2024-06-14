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
        String title = "Детство";
        Long authorId = 2L;

        CreateBookResponse response = bookSteps.createBook(title, authorId);

        BookAssertions.verifyStatusCode(response.getHttpStatusCode(), 201);
        BookAssertions.verifyCreateBookResponse(response);
    }
    @Test
    @DisplayName("Негативный тест - Создание книги без указания названия")
    @Description("Проверка, что при попытке создать книгу без названия возвращается ошибка")
    public void testCreateBookWithoutTitle() {
        Long authorId = 3L;
        String title = null;

        CreateBookResponse response = ErrorBookApiRequests.createBookWithError(authorId, title, 400);

        BookAssertions.verifyStatusCode(response.getHttpStatusCode(), 400);
        BookAssertions.verifyFailedCreateBookResponse(response,400, 1001,"Не передано наименование книги",null);
    }

    @Test
    @DisplayName("Негативный тест - Создание книги с несуществующим автором")
    @Description("Проверка, что при попытке создать книгу с несуществующим автором возвращается ошибка")
    public void testCreateBookWithNonExistingAuthor() {
        Long authorId = 999L;
        String title = "Детство";

        CreateBookResponse response = ErrorBookApiRequests.createBookWithError(authorId, title, 409);

        BookAssertions.verifyStatusCode(response.getHttpStatusCode(), 409);
        BookAssertions.verifyFailedCreateBookResponse(response, 409, 1004, "Указанный автор не существует в таблице",null );
    }

    @Test
    @DisplayName("Негативный тест - Создание книги с ошибкой сохранения")
    @Description("Проверка, что при ошибке сохранения возвращается ошибка сервера")
    public void testCreateBookWithSavingError() {
        Long authorId = 5L;
        String title = "Детство";

        CreateBookResponse response = ErrorBookApiRequests.createBookWithError(authorId, title, 500);

        BookAssertions.verifyStatusCode(response.getHttpStatusCode(), 500);
        BookAssertions.verifyFailedCreateBookResponse(response, 500, 1003, "Ошибка сохранения данных",null);
    }
}