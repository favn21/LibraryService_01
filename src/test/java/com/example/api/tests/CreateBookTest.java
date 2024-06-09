package com.example.api.tests;

import com.example.api.tests.steps.BookSteps;
import com.example.api.models.response.CreateBookResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("LibraryService")
@Story("Cоздать книгу")
public class CreateBookTest extends BaseTest {

    private final BookSteps bookSteps = new BookSteps();

    @Test
    @DisplayName("Сохранение новой книги")
    public void testCreateBook() {
        String title = "Детство";
        Long authorId = 653L;

        CreateBookResponse response = bookSteps.createBook(title, authorId);

        bookSteps.verifyCreateBookResponse(response);
    }
}