package com.example.api.steps;

import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.service.RequestBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ErrorBookApiRequests {

    private static final String basePath = "/books";

    public static Response createBookWithError(Long authorId, String title, int expectedStatusCode) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        Response response = given()
                .basePath(basePath)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/save")
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();

        return response;
    }

    public static Response getBooksByAuthorWithError(Long authorId, int expectedStatusCode) {
        Response response = given()
                .pathParam("id", authorId)
                .when()
                .get("/authors/{id}"+basePath)
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();

        return response;
    }

    public static Response getBooksByAuthorWithErrorAndMock(String authorId, int expectedStatusCode) {
        return given()
                .accept(ContentType.JSON)
                .pathParam("id", authorId)
                .when()
                .get("/authors/{id}"+basePath)
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();

    }

    public static Response createBookWithErrorAndMock(Long authorId, String title, int statusCode) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        Response response;

        if (title.equals("Детство") && authorId == 2L) {
            response = given()
                    .basePath(basePath)
                    .contentType(ContentType.JSON)
                    .body(request)
                    .when()
                    .post("/save")
                    .then()
                    .statusCode(500)
                    .extract()
                    .response();
        } else {
            response = given()
                    .basePath(basePath)
                    .contentType(ContentType.JSON)
                    .body(request)
                    .when()
                    .post("/save")
                    .then()
                    .statusCode(statusCode)
                    .extract()
                    .response();
        }

        return response;
    }
}