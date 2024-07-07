package com.example.api.service;

import com.example.api.models.request.TokenRequest;
import com.example.api.models.response.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenService {

    private static final String AUTH_URL = "http://localhost:8080/auth/login";

    public static String getAuthToken(String login, String password) {

        TokenRequest authRequest = new TokenRequest(login, password);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when()
                .post(AUTH_URL)
                .then().log().all()
                .extract()
                .response();

        TokenResponse tokenResponse = response.as(TokenResponse.class);//
        return tokenResponse.getToken();
    }
}