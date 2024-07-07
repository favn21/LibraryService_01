package com.example.api.steps;

import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.service.RequestBuilder;

import com.example.api.models.response.GetBooksByAuthorResponse.BookDetail;
import com.example.api.service.TokenService;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BookApiRequests {

    static String login = "master_log";
    static String password = "qweasdzxc";
    static String authToken;

    public static CreateBookResponse createBook(String title, Long authorId, int expectedStatusCode) {
        if (authToken == null) {
            authToken = TokenService.getAuthToken(login, password);
        }

        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        return given()
                .spec(RequestBuilder.requestSpecCreateBook(request))
                .header("Authorization", "Bearer " + authToken)
                .body(request)
                .when()
                .post("/books/save")
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(CreateBookResponse.class);
    }

    public static List<BookDetail> getBooksByAuthor(Long authorId, int statusCode) {
        if (authToken == null) {
            authToken = TokenService.getAuthToken(login, password);
        }

        return given()
                .spec(RequestBuilder.requestSpecGetBooksByAuthor(authorId))
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get()
                .then()
                .statusCode(statusCode)
                .extract()
                .jsonPath()
                .getList(".", BookDetail.class);
    }
}
