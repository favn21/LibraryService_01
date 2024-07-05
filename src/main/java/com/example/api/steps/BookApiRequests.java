package com.example.api.steps;

import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.service.RequestBuilder;

import com.example.api.models.response.GetBooksByAuthorResponse.BookDetail;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BookApiRequests {

    public CreateBookResponse createBook(String title, Long authorId, int expectedStatusCode) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        return given()
                .spec(RequestBuilder.requestSpecCreateBook(request))
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(CreateBookResponse.class);
    }

    public static List<BookDetail> getBooksByAuthor(Long authorId, int statusCode) {
        return given()
                .spec(RequestBuilder.requestSpecGetBooksByAuthor(authorId))
                .when()
                .get()
                .then()
                .statusCode(statusCode)
                .extract()
                .jsonPath()
                .getList(".", BookDetail.class);

    }
}
