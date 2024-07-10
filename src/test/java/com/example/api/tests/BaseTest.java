package com.example.api.tests;


import com.example.api.service.TokenService;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;




public abstract class BaseTest {
    private static final int PORT = 8080;
    private static TokenService tokenService;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = getBaseURI();

        tokenService = new TokenService();

    }

    protected static String getBaseURI() {
        return "http://localhost:" + PORT;
    }
}

