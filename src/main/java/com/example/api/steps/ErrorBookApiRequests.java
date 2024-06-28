package com.example.api.steps;

import com.example.api.models.request.CreateBookRequest;
import com.example.api.service.RequestBuilder;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class ErrorBookApiRequests {


    public static Response createBookWithError(Long authorId, String title, int expectedStatusCode) {

        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);


        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseStatusCode(expectedStatusCode));


        return given()
                .spec(RequestBuilder.requestSpec())
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post("/books/save")
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    public static Response getBooksByAuthorWithError(Long authorId, int expectedStatusCode) {

        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseStatusCode(expectedStatusCode));


        return given()
                .spec(RequestBuilder.requestSpec())
                .pathParam("id", authorId)
                .when()
                .get("/authors/{id}/books")
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }


    public static Response getBooksByAuthorWithErrorAndMock(Long authorId, int statusCode) {

        return given()
                .spec(RequestBuilder.requestSpec())
                .pathParam("id", authorId)
                .when()
                .get("/authors/{id}/books")
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
    }
    public static Response createBookWithErrorAndMock(Long authorId, String title, int statusCode) {
        return given()
                .spec(RequestBuilder.requestSpec())
                .contentType(ContentType.JSON)
                .when()
                .post("/books/save")
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
    }
}
