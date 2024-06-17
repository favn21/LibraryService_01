package com.example.api.steps;



import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.CreateBookResponse;

import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.service.RequestBuilder;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;



public class ErrorBookApiRequests {
    public static CreateBookResponse createBookWithError(Long authorId, String title, int expectedStatusCode) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecError(expectedStatusCode));

        Response response = given()
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post("/books/save")
                .andReturn();

        CreateBookResponse createBookResponse = response.then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(CreateBookResponse.class);

        createBookResponse.setHttpStatusCode(response.statusCode());
        return createBookResponse;
    }


    public static GetBooksByAuthorResponse getBooksByAuthorWithError(Long authorId, int expectedStatusCode) {
        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecError(expectedStatusCode));

        Response response = given()
                .pathParam("id", authorId)
                .when()
                .get("/authors/{id}/books")
                .andReturn();

        GetBooksByAuthorResponse getBooksByAuthorResponse = response.then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(GetBooksByAuthorResponse.class);
        getBooksByAuthorResponse.setHttpStatusCode(expectedStatusCode);
        return getBooksByAuthorResponse;
    }
}

