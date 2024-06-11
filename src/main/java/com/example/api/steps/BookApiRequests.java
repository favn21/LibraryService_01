package com.example.api.steps;

import com.example.api.db.Book;
import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.request.GetBooksByAuthorRequest;
import com.example.api.service.RequestBuilder;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

import java.util.List;

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
                .statusCode(200)
                .extract()
                .as(CreateBookResponse.class);
    }

    public List<Book> getBooksByAuthor(Long authorId, ContentType contentType) {
        GetBooksByAuthorRequest request = new GetBooksByAuthorRequest();
        request.setAuthorId(authorId);

        Response response = given()
                .spec(RequestBuilder.requestSpec())
                .body(request)
                .accept(contentType)
                .when()
                .get("/authors/{id}/books", authorId)
                .andReturn();

        if (ContentType.JSON.equals(contentType)) {
            return response.then().extract().jsonPath().getList(".", Book.class);
        } else if (ContentType.XML.equals(contentType)) {
            return response.then().extract().xmlPath().getList("books.book", Book.class);
        } else {
            throw new IllegalArgumentException("Unsupported content type: " + contentType);
        }
    }

}