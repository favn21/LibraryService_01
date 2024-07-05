package com.example.api.steps;

import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.BaseResponse;
import com.example.api.service.RequestBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class ErrorBookApiRequests {


    public static BaseResponse createBookWithError(Long authorId, String title, int expectedStatusCode) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        Response response = given()
                .spec(RequestBuilder.requestSpecCreateBook(request))
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();

        return response.as(BaseResponse.class);
    }

    public static BaseResponse getBooksByAuthorWithError(Long authorId, int expectedStatusCode) {
        Response response = given()
                .spec(RequestBuilder.requestSpecGetBooksByAuthor(authorId))
                .when()
                .get()
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();

        return response.as(BaseResponse.class);
    }

    public static BaseResponse createBookWithErrorAndMock(Long authorId, String title, int statusCode) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        Response response;

        if (title.equals("Детство") && authorId == 2L) {
            response = given()
                    .spec(RequestBuilder.requestSpecCreateBook(request))
                    .contentType(ContentType.JSON)
                    .body(request)
                    .when()
                    .post()
                    .then()
                    .statusCode(500)
                    .extract()
                    .response();
        } else {
            response = given()
                    .spec(RequestBuilder.requestSpecCreateBook(request))
                    .contentType(ContentType.JSON)
                    .body(request)
                    .when()
                    .post()
                    .then()
                    .statusCode(statusCode)
                    .extract()
                    .response();
        }

        return response.as(BaseResponse.class);
    }
}