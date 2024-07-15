package com.example.api.steps;

import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.CreateBookResponse;
import com.example.api.service.RequestBuilder;

import com.example.api.models.response.GetBooksByAuthorResponse.BookDetail;
import com.example.api.service.TokenService;

import java.util.List;



public class BookApiRequests {

    static String login = "master_log";
    static String password = "qweasdzxc";
    static String authToken;

    private static void ensureAuthToken() {
        if (authToken == null) {
            authToken = TokenService.getAuthToken(login, password);
        }
    }

    public static CreateBookResponse createBook(String title, Long authorId, int expectedStatusCode) {
        ensureAuthToken();

        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        return RequestBuilder.getRequestSpec(request, authToken)
                .spec(RequestBuilder.requestSpecCreateBook(request, authToken))
                .when()
                .post()
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(CreateBookResponse.class);
    }

    public static List<BookDetail> getBooksByAuthor(Long authorId, int statusCode) {
        ensureAuthToken();

        return RequestBuilder.getRequestSpec(authToken)
                .spec(RequestBuilder.requestSpecGetBooksByAuthor(authorId, authToken))
                .when()
                .get()
                .then()
                .statusCode(statusCode)
                .extract()
                .jsonPath()
                .getList(".", BookDetail.class);
    }
}
