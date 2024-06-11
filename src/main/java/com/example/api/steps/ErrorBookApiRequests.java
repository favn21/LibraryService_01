package com.example.api.steps;



import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.CreateBookResponse;

import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.service.RequestBuilder;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;



public class ErrorBookApiRequests {
    public static CreateBookResponse createBookWithoutTitle(Long authorId) {
        CreateBookRequest request = new CreateBookRequest();
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecError(400));

        return given()
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post("/books/save")
                .then()
                .extract()
                .as(CreateBookResponse.class);
    }
    public static CreateBookResponse createBookWithNonExistingAuthor(Long authorId) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle("Детство");
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecError(409));

        return given()
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post("/books/save")
                .then()
                .extract()
                .as(CreateBookResponse.class);
    }
    public static CreateBookResponse createBookWithSavingError(Long authorId) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle("Детство");
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecError(500));

        return given()
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post("/books/save")
                .then()
                .extract()
                .as(CreateBookResponse.class);
    }
    public static GetBooksByAuthorResponse getBooksByAuthorWithoutId() {
        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecError(400));

        Response response = given()
                .when()
                .get("/authors/books")
                .andReturn();

        return response.then()
                .extract()
                .as(GetBooksByAuthorResponse.class);
    }


    public static GetBooksByAuthorResponse getBooksByAuthorWithNonexistentId(Long authorId) {
        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecError(400));

        Response response = given()
                .pathParam("id", authorId)
                .when()
                .get("/authors/{id}/books")
                .andReturn();

        return response.then()
                .extract()
                .as(GetBooksByAuthorResponse.class);
    }

    public static GetBooksByAuthorResponse getBooksByAuthorWithErrorInDataRetrieval(Long authorId) {
        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecError(500));

        Response response = given()
                .pathParam("id", authorId)
                .when()
                .get("/authors/{id}/books")
                .andReturn();

        return response.then()
                .extract()
                .as(GetBooksByAuthorResponse.class);
    }
}

