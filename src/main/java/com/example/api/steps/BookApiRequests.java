package com.example.api.steps;


import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.request.GetBooksByAuthorRequest;
import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.service.RequestBuilder;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class BookApiRequests {

    public CreateBookResponse createBook(String title, Long authorId) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        return given()
                .spec(RequestBuilder.requestSpec())
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post("/books/save")
                .then()
                .statusCode(201)
                .extract()
                .as(CreateBookResponse.class);
    }

    public GetBooksByAuthorResponse getBooksByAuthor(Long authorId, ContentType contentType) {
        GetBooksByAuthorRequest request = new GetBooksByAuthorRequest();
        request.setAuthorId(authorId);

        Response response = given()
                .spec(RequestBuilder.requestSpec())
                .body(request)
                .accept(contentType)
                .when()
                .get("/authors/{id}/books", authorId)
                .andReturn();
        GetBooksByAuthorResponse getBooksResponse = new GetBooksByAuthorResponse();
        if (ContentType.JSON.equals(contentType)) {
            getBooksResponse.setBooks(response.then().extract().jsonPath().getList(".", GetBooksByAuthorResponse.BookDetail.class));
        } else if (ContentType.XML.equals(contentType)) {
            getBooksResponse.setBooks(response.then().extract().xmlPath().getList("books.book", GetBooksByAuthorResponse.BookDetail.class));
        } else {
            throw new IllegalArgumentException("Unsupported content type: " + contentType);
        }
        return getBooksResponse;
    }

}