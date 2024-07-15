package com.example.api.steps;

import com.example.api.models.request.CreateBookRequest;
import com.example.api.models.response.BaseResponse;
import com.example.api.service.RequestBuilder;
import com.example.api.service.TokenService;

import io.restassured.response.Response;



public class ErrorBookApiRequests {
    static String login = "master_log";
    static String password = "qweasdzxc";

    static String authToken;

    public static BaseResponse createBookWithError(Long authorId, String title, int expectedStatusCode) {
        if (authToken == null) {
            authToken = TokenService.getAuthToken(login, password);
        }

        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        Response response =RequestBuilder.getRequestSpec(request, authToken)
                .spec(RequestBuilder.requestSpecCreateBook(request, authToken))
                .when()
                .post()
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();

        return response.as(BaseResponse.class);
    }

    public static BaseResponse getBooksByAuthorWithError(Long authorId, int expectedStatusCode) {
        if (authToken == null) {
            authToken = TokenService.getAuthToken(login, password);
        }

        Response response = RequestBuilder.getRequestSpec(authToken)
                .spec(RequestBuilder.requestSpecGetBooksByAuthor(authorId, authToken))
                .when()
                .get()
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();

        return response.as(BaseResponse.class);
    }

    public static BaseResponse createBookWithErrorAndMock(Long authorId, String title, int statusCode) {
        if (authToken == null) {
            authToken = TokenService.getAuthToken(login, password);
        }

        CreateBookRequest request = new CreateBookRequest();
        request.setBookTitle(title);
        CreateBookRequest.Author author = new CreateBookRequest.Author();
        author.setId(authorId);
        request.setAuthor(author);

        Response response;

        if (title.equals("Детство") && authorId == 2L) {
            response = RequestBuilder.getRequestSpec(request, authToken)
                    .spec(RequestBuilder.requestSpecCreateBook(request, authToken))
                    .when()
                    .post()
                    .then()
                    .statusCode(500)
                    .extract()
                    .response();
        } else {
            response = RequestBuilder.getRequestSpec(request, authToken)
                    .spec(RequestBuilder.requestSpecCreateBook(request, authToken))
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