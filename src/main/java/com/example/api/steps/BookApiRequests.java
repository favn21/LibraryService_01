package com.example.api.steps;

import com.example.api.models.request.CreateBookRequest;
import com.example.api.service.RequestBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookApiRequests {


    public static Response createBook(String title, Long authorId) {

        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        return given()
                .spec(RequestBuilder.requestSpec())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/books/save")
                .then()
                .extract()
                .response();
    }

    public static Response getBooksByAuthor(Long authorId, ContentType contentType) {

        return given()
                .spec(RequestBuilder.requestSpec())
                .accept(contentType)
                .pathParam("id", authorId)
                .when()
                .get("/authors/{id}/books")
                .then()
                .extract()
                .response();
    }
}
