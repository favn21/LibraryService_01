package com.example.api.tests;

import com.example.api.database.DatabaseHelper;
import com.example.api.db.Author;
import com.example.api.db.Book;
import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.steps.BookApiRequests;
import com.example.api.steps.ErrorBookApiRequests;
import com.example.api.assertions.BookAssertions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;


@Epic("LibraryService")
@Story("Получить список книг по ID автора")
public class GetBooksByAuthorTest extends BaseTest {

    BookAssertions bookAssertions = new BookAssertions(entityManager);
    @BeforeEach
    public void setUp() {
        super.setUp();
        bookAssertions = new BookAssertions(entityManager);
    }

    @Test
    @DisplayName("Позитивный тест - Получение книг по автору(JSON), когда у автора есть книги")
    @Description("Проверка, что при запросе книг по автору, у которого уже есть книги, возвращаются все книги этого автора")//чтото не то с тестом
    public void testGetBooksByAuthorWithExistingBooks() {
        long authorId = 1L;

        bookRepository.insertBook("Книга 1", authorId);
        bookRepository.insertBook("Книга 2", authorId);

        List<GetBooksByAuthorResponse.BookDetail> response = BookApiRequests.getBooksByAuthor(authorId, 200);
        List<Book> booksInDb = DatabaseHelper.getRecordsByField(Book.class, "author_id", authorId);

        List<GetBooksByAuthorResponse.BookDetail> expectedBooks = booksInDb.stream()
                .map(book -> {
                    GetBooksByAuthorResponse.AuthorDetail authorDetail = new GetBooksByAuthorResponse.AuthorDetail();
                    authorDetail.setId(book.getAuthor_id());
                    authorDetail.setFirstName("Имя");
                    authorDetail.setSecondName("Отчество");
                    authorDetail.setFamilyName("Фамилия");
                    authorDetail.setBirthDate(LocalDate.now());

                    GetBooksByAuthorResponse.BookDetail bookDetail = new GetBooksByAuthorResponse.BookDetail();
                    bookDetail.setId(book.getId());
                    bookDetail.setBookTitle(book.getBookTitle());
                    bookDetail.setAuthor(authorDetail);

                    return bookDetail;
                })
                .collect(Collectors.toList());
        bookAssertions.verifyGetBooksByAuthorResponse(response, expectedBooks);
        bookAssertions.verifyBooksByAuthorId(authorId, expectedBooks);
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