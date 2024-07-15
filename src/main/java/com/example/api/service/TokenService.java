package com.example.api.service;

import com.example.api.models.request.TokenRequest;
import com.example.api.models.response.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private static final String AUTH_URL = "http://localhost:8080/api/auth/login"; // Проверьте, что этот URL правильный

    public static String getAuthToken(String login, String password) {

        logger.debug("Sending authentication request for login: {}", login);

        TokenRequest authRequest = new TokenRequest(login, password);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when()
                .get(AUTH_URL)
                .then()
                .extract()
                .response();

        if (response.statusCode() != 200) {
            logger.warn("Failed to authenticate: {}", response.getStatusLine());
            throw new RuntimeException("Failed to authenticate: " + response.getStatusLine());
        }

        TokenResponse tokenResponse = response.as(TokenResponse.class);
        logger.info("Successfully authenticated. Token: {}", tokenResponse.getToken());
        return tokenResponse.getToken();
    }
}