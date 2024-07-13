package com.example.api.tests;

import com.example.api.database.DatabaseHelper;
import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.steps.BookApiRequests;
import com.example.api.steps.ErrorBookApiRequests;
import com.example.api.assertions.BookAssertions;
import io.qameta.allure.Description;
import com.example.api.db.Book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.api.service.RequestBuilder.*;

public class CreateBookTest extends BaseTest {

    private final BookApiRequests bookSteps = new BookApiRequests();

    @Test
    @DisplayName("Позитивный тест - Сохранение новой книги")
    @Description("Проверка, что книга успешно создается с валидными данными")
    public void testCreateBook() {
        installSpecification(requestSpec(), responseStatusCode(201));
        CreateBookResponse response = bookSteps.createBook("Детство", 2L, 201);
        BookAssertions.verifyCreateBookResponse(response);
        BookAssertions.verifyBookInDatabase(response.getBookId(), "Детство", 2L);
    }

    @Test
    @DisplayName("Негативный тест - Создание книги без указания названия")
    @Description("Проверка, что при попытке создать книгу без названия возвращается ошибка")
    public void testCreateBookWithoutTitle() {
        installSpecification(requestSpec(), responseStatusCode(400));
        BaseResponse response = ErrorBookApiRequests.createBookWithError(3L, null, 400);
        BookAssertions.verifyFailedResponse(response, "1001", "Не передан обязательный параметр: bookTitle", "Не передано наименование книги");
        BookAssertions.verifyBookNotInDatabase(3L);
    }

    @Test
    @DisplayName("Негативный тест - Создание книги с несуществующим автором")
    @Description("Проверка, что при попытке создать книгу с несуществующим автором возвращается ошибка")
    public void testCreateBookWithNonExistingAuthor() {
        installSpecification(requestSpec(), responseStatusCode(409));
        BaseResponse response = ErrorBookApiRequests.createBookWithError(999L, "Детство", 409);
        BookAssertions.verifyFailedResponse(response, "1004", "Указанный автор не существует в таблице", null);
        BookAssertions.verifyBookNotInDatabase(999L);
    }

    @Test
    @DisplayName("Негативный тест - Сохранение уже существующей книги")
    @Description("Проверка, что при попытке сохранить книгу, которая уже существует, возвращается ошибка конфликта")
    public void testCreateBookWithSavingError() {
        installSpecification(requestSpec(), responseStatusCode(500));
        BaseResponse response = ErrorBookApiRequests.createBookWithErrorAndMock(2L, "Детство", 500);
        BookAssertions.verifyFailedResponse(response, "1003", "Книга уже существует в библиотеке", null);

        List<Book> booksInDb = DatabaseHelper.getRecordsByField(Book.class, "bookTitle", "Детство");
        BookAssertions.bookListSize(1, booksInDb);
    }
}
