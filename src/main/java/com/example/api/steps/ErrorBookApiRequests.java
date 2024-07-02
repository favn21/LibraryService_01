package com.example.api.steps;

import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.BaseResponse;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.service.RequestBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class ErrorBookApiRequests {

    public static CreateBookResponse createBookWithError(Long authorId, String title, int expectedStatusCode) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        Response response = given()
                .spec(RequestBuilder.requestSpec())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/books/save")
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();

        return response.as(CreateBookResponse.class);
    }

    public static GetBooksByAuthorResponse getBooksByAuthorWithError(Long authorId, int expectedStatusCode) {
        Response response = given()
                .spec(RequestBuilder.requestSpec())
                .pathParam("id", authorId)
                .when()
                .get("/authors/{id}/books")
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();

        return response.as(GetBooksByAuthorResponse.class);
    }

    public static GetBooksByAuthorResponse getBooksByAuthorWithErrorAndMock(Long authorId, int statusCode) {
        Response response = given()
                .spec(RequestBuilder.requestSpec())
                .pathParam("id", authorId)
                .when()
                .get("/authors/{id}/books")
                .then()
                .statusCode(statusCode)
                .extract()
                .response();

        return response.as(GetBooksByAuthorResponse.class);
    }
    public static CreateBookResponse createBookWithErrorAndMock(Long authorId, String title, int statusCode) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        RequestSpecification requestSpec = RequestBuilder.requestSpec();
        ResponseSpecification responseSpec = RequestBuilder.responseStatusCode(statusCode);
        RequestBuilder.installSpecification(requestSpec, responseSpec);


        Response response = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/books/saveWithError")
                .then()
                .extract()
                .response();


        return response.as(CreateBookResponse.class);
    }

}