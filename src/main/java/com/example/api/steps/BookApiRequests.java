package com.example.api.steps;


import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.models.request.GetBooksByAuthorRequest;
import com.example.api.models.response.GetBooksByAuthorResponse;
import com.example.api.service.RequestBuilder;


import static com.example.api.service.RequestBuilder.responseSpecOK201;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;


public class BookApiRequests {
    public static CreateBookResponse createBook(String title, Long authorId) {
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
                .spec(responseSpecOK201())
                .extract()
                .as(CreateBookResponse.class);
    }

    public static GetBooksByAuthorResponse getBooksByAuthor(Long authorId, ContentType contentType) {
        GetBooksByAuthorRequest request = new GetBooksByAuthorRequest();
        request.setAuthorId(authorId);

        return given()
                .spec(RequestBuilder.requestSpec())
                .body(request)
                .accept(contentType)
                .when()
                .get("/authors/{id}/books", authorId)
                .then()
                .spec(responseSpecOK201())
                .extract()
                .as(GetBooksByAuthorResponse.class);
    }

}