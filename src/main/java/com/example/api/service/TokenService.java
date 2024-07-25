package com.example.api.service;

import com.example.api.models.request.TokenRequest;
import com.example.api.models.response.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class TokenService {

    private static final String AUTH_URL = "http://localhost:8080/auth/login";

    public static String getAuthToken(String login, String password) {
        TokenRequest authRequest = new TokenRequest(login, password);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .body(authRequest)
                .when()
                .get(AUTH_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();

        TokenResponse tokenResponse = response.as(TokenResponse.class);
        return tokenResponse.getJwtToken();
    }
}
