package com.example.api.tests;

import com.example.api.db.Book;
import com.example.api.tests.steps.BookSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
@Epic("LibraryService")
@Story("Получить список книг по ID автора")
public class GetBooksByAuthorTest extends BaseTest {

    private final BookSteps bookSteps = new BookSteps();

    @Test
    @DisplayName("Получение книг по автору (JSON)")
    public void testGetBooksByAuthorJSON() {
        Long authorId = 653L;
        String contentType = "application/json";

        List<Book> books = bookSteps.getBooksByAuthor(authorId, contentType);

        bookSteps.verifyGetBooksByAuthorResponse(books);
    }

    @Test
    @DisplayName("Получение книг по автору (XML)")
    public void testGetBooksByAuthorXML() {
        Long authorId = 653L;
        String contentType = "application/xml";

        List<Book> books = bookSteps.getBooksByAuthor(authorId, contentType);

        bookSteps.verifyGetBooksByAuthorResponse(books);
    }
}