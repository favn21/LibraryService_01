package com.example.api.tests.steps;

import com.example.api.db.Book;
import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.request.GetBooksByAuthorRequest;
import com.example.api.service.RequestBuilder;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

public class BookSteps {

    public CreateBookResponse createBook(String title, Long authorId) {
        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/books/save")
                .then()
                .statusCode(200)
                .extract()
                .as(CreateBookResponse.class);
    }

    public void verifyCreateBookResponse(CreateBookResponse response) {
        assertNotNull(response);
        assertNotNull(response.getBookId());
    }

    public List<Book> getBooksByAuthor(Long authorId, String contentType) {
        GetBooksByAuthorRequest request = new GetBooksByAuthorRequest();
        request.setAuthorId(authorId);

        Response response = given()
                .spec(RequestBuilder.requestSpec()) // Using base URL from RequestBuilder
                .body(request)
                .accept(contentType)
                .when()
                .get("/authors/{id}/books", authorId)
                .andReturn();

        if ("application/json".equals(contentType)) {
            return response.then().extract().jsonPath().getList(".", Book.class);
        } else if ("application/xml".equals(contentType)) {
            return response.then().extract().xmlPath().getList("books.book", Book.class);
        } else {
            throw new IllegalArgumentException("Unsupported content type: " + contentType);
        }
    }

    public void verifyGetBooksByAuthorResponse(List<Book> books) {
        assertNotNull(books);
        assertNotNull(books.get(0).getId());
        assertNotNull(books.get(0).getBookTitle());
    }
}