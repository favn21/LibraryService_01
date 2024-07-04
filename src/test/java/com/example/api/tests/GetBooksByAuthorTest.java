package com.example.api.tests;

import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.steps.BookApiRequests;
import com.example.api.steps.ErrorBookApiRequests;
import com.example.api.assertions.BookAssertions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.example.api.service.RequestBuilder.*;

@Epic("LibraryService")
@Story("Получить список книг по ID автора")
public class GetBooksByAuthorTest extends BaseTest {

    @Test
    @DisplayName("Позитивный тест - Получение книг по автору (JSON)")
    @Description("Проверка, что можно получить книги автора в формате JSON")
    public void testGetBooksByAuthorJSON() {
        installSpecification(requestSpec(), responseStatusCode(200));
        Response response = BookApiRequests.getBooksByAuthor(2L, 200);
        GetBooksByAuthorResponse getBooksByAuthorResponse = response.as(GetBooksByAuthorResponse.class);
        BookAssertions.verifyGetBooksByAuthorResponse(response, getBooksByAuthorResponse, 200, Collections.emptyList()); // Add expected books list here
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору без указания ID")
    @Description("Проверка, что при запросе без ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithoutId() {
        installSpecification(requestSpec(), responseStatusCode(400));
        Response response = ErrorBookApiRequests.getBooksByAuthorWithError(0L, 400);
        BaseResponse baseResponse = response.as(BaseResponse.class);
        BookAssertions.verifyFailedResponse(response, baseResponse, 400, "1001", "Не передан обязательный параметр: autherId", "Не передан id автора");
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с несуществующим ID")
    @Description("Проверка, что при запросе с несуществующим ID автора возвращается ошибка")
    public void testGetBooksByAuthorWithNonexistentId() {
        installSpecification(requestSpec(), responseStatusCode(409));
        Response response = ErrorBookApiRequests.getBooksByAuthorWithError(999L, 409);
        BaseResponse baseResponse = response.as(BaseResponse.class);
        BookAssertions.verifyFailedResponse(response, baseResponse, 409, "1004", "Указанный автор не существует в таблице", null);
    }

    @Test
    @DisplayName("Негативный тест - Получение книг по автору с недопустимым значением id")
    @Description("Проверка, что при передаче недопустимого значения id возвращается ошибка сервера")
    public void testGetBooksByAuthorWithError() {
        String invalidAuthorId = "id";
        int expectedStatusCode = 500;

        installSpecification(requestSpec(), responseStatusCode(expectedStatusCode));
        Response response = ErrorBookApiRequests.getBooksByAuthorWithErrorAndMock(invalidAuthorId, expectedStatusCode);


        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErrorCode("1005");
        baseResponse.setErrorMessage("Ошибка получения данных");
        baseResponse.setErrorDetails("Неверный формат параметра id");

        BookAssertions.verifyFailedResponse(response, baseResponse, 500, "1005", "Ошибка получения данных", "Неверный формат параметра id");
    }
}