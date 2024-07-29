package com.example.api.tests;

import com.example.api.database.DatabaseHelper;
import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.steps.BookApiRequests;
import com.example.api.steps.ErrorBookApiRequests;
import com.example.api.assertions.BookAssertions;
import io.qameta.allure.Description;
import com.example.api.db.Book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


public class CreateBookTest extends BaseTest {

    private final BookApiRequests bookSteps = new BookApiRequests();
    BookAssertions bookAssertions = new BookAssertions(entityManager);
    @BeforeEach
    public void setUp() {
        super.setUp();
        bookAssertions = new BookAssertions(entityManager);
    }

    @Test
    @DisplayName("Позитивный тест - Сохранение новой книги")
    @Description("Проверка, что книга успешно создается с валидными данными")
    public void testCreateBook() {
        CreateBookResponse response = bookSteps.createBook("Детство", 2L, 201);
        bookAssertions.verifyCreateBookResponse(response);
        Long bookId = response.getBookId();
        bookAssertions.verifyBookInDatabase(bookId, "Детство", 2L);
    }

    @Test
    @DisplayName("Негативный тест - Создание книги без указания названия")
    @Description("Проверка, что при попытке создать книгу без названия возвращается ошибка")
    public void testCreateBookWithoutTitle() {
        BaseResponse response = ErrorBookApiRequests.createBookWithError(3L, null, 400);
        bookAssertions.verifyFailedResponse(response, "1001", "Не передан обязательный параметр: bookTitle", "Не передано наименование книги");
        bookAssertions.verifyBookNotInDatabase(null);
    }

    @Test
    @DisplayName("Негативный тест - Создание книги с несуществующим автором")
    @Description("Проверка, что при попытке создать книгу с несуществующим автором возвращается ошибка")
    public void testCreateBookWithNonExistingAuthor() {
        BaseResponse response = ErrorBookApiRequests.createBookWithError(999L, "Детство", 409);
        bookAssertions.verifyFailedResponse(response, "1004", "Указанный автор не существует в таблице", null);
        bookAssertions.verifyNoBooksInDatabaseWithAuthorId(999L);
    }

    @Test
    @DisplayName("Негативный тест - Сохранение уже существующей книги")
    @Description("Проверка, что при попытке сохранить книгу, которая уже существует, возвращается ошибка конфликта")
    public void testCreateBookWithSavingError() {
        BaseResponse response = ErrorBookApiRequests.createDuplicateBook(2L, "Детство", 500);
        bookAssertions.verifyFailedResponse(response, "1003", "Книга уже существует в библиотеке", null);

        List<Book> booksInDb = DatabaseHelper.getRecordsByField(Book.class, "bookTitle", "Детство");
        bookAssertions.bookListSize(1, booksInDb);
    }
}
