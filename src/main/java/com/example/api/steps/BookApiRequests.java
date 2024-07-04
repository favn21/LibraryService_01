package com.example.api.steps;

import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.service.RequestBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.example.api.models.response.GetBooksByAuthorResponse.BookDetail;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BookApiRequests {

    private static final String basePath = "/books";

    public CreateBookResponse createBook(String title, Long authorId, int expectedStatusCode) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        return given()
                .spec(RequestBuilder.requestSpec())
                .basePath(basePath)
                .body(request)
                .when()
                .post("/save")
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(CreateBookResponse.class);
    }

    public static Response getBooksByAuthor(Long authorId, int statusCode) {
        Response response = given()
                .spec(RequestBuilder.requestSpec())
                .pathParam("id", authorId)
                .when()
                .get("/authors/{id}"+basePath)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();

        return response;
    }
}